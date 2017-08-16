package com.majoinen.d.pwcrypt.database;

import com.majoinen.d.database.DatabaseController;
import com.majoinen.d.database.exception.DBUtilsException;
import com.majoinen.d.encryption.exception.EncryptionUtilsException;
import com.majoinen.d.encryption.pbe.PBEKeyBuilder;
import com.majoinen.d.encryption.utils.Cipher;
import com.majoinen.d.encryption.utils.EncryptionKeyGenerator;
import com.majoinen.d.encryption.utils.Tools;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * DAO for the User table.
 *
 * @author Daniel Majoinen
 * @version 1.0, 25/7/17
 */
public class AccountDao {

    // Size of the initialization vectors
    private static final int INIT_VECTOR_SIZE = 16;

    // Size of the salt when hashing
    private static final int SALT_SIZE = 32;

    // Encryption key length
    private static final int KEY_LENGTH = 256;

    // Minimum number of iterations for hashing algorithms
    private static final int MIN_ITERATIONS = 100000;

    // Maximum number of iterations for hashing algorithms
    private static final int MAX_ITERATIONS = 200000;

    // Algorithm to use creating a key
    private static final String KEY_ALGORITHM = "AES";

    // Algorithm to use when encrypting and decrypting
    private static final String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final String ADD_DATA_KEY =
      "INSERT INTO `data_key` ('description', 'data_key') " +
        "VALUES ('MASTER KEY', :data_key)";

    // SQL query to add a new user to the database
    private static final String ADD_USER_QUERY =
      "INSERT INTO `account` ('account_uuid', 'email', 'data_key_id') " +
        "VALUES (:account_uuid, :email, (SELECT last_insert_rowid()))";

    // SQL query to get the users data_key entry
    private static final String SELECT_DATA_KEY_QUERY =
      "SELECT `data_key` FROM `data_key`, `account` WHERE email = :email";

    private static Map<DatabaseController, AccountDao> map;
    private DatabaseController databaseController;

    private AccountDao(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }

    /**
     * Cache all instances of AccountDao mapped by database controller.
     *
     * @param databaseController The database controller which this will utilise
     * @return A AccountDao mapped to the specific DatabaseController supplied,
     * or a new one instance be created.
     */
    public static AccountDao getInstance(DatabaseController databaseController) {
        if(map == null)
            map = new HashMap<>();
        else if(map.containsKey(databaseController))
            return map.get(databaseController);
        AccountDao accountDao = new AccountDao(databaseController);
        map.put(databaseController, accountDao);
        return accountDao;
    }

    /**
     * Add a new user to the database. The steps to add the user are as follows:
     * 1. Generate the user a UUID
     * 2. Generate the user an encryption key - we call it the DataKey
     * 3. Generate a salt, iv and iteration count for the users password and
     *    DataKey
     * 4. Build an encryption key with the users password to encrypt the DataKey
     * 5. Encrypt the DataKey with the created encryption key
     * 6. Hash then encrypt the users password
     * 7. Store both the encrypted DataKey and Password in the database,
     *    along with all necessary decryption info
     * 8. Clean all sensitive data
     *
     * @param email The email for the new user.
     * @param password The password for the new user.
     * @return True if the user is successfully added, or false otherwise.
     * @throws Exception
     */
    public boolean addNewUser(String email, char[] password) throws EncryptionUtilsException, DBUtilsException {
        /* Generate the account UUID */
        UUID uuid = UUID.randomUUID(); // TODO: Receive from cloud server

        /* Generate a random encryption key for the new user */
        SecretKey dataKey = EncryptionKeyGenerator
          .generateRandomKey(KEY_ALGORITHM, KEY_LENGTH);

        /* Generate IV, salts & iterations for data key */
        byte[] dataKeySalt = Tools.generateRandomBytes(SALT_SIZE);
        byte[] dataKeyIV = Tools.generateRandomBytes(INIT_VECTOR_SIZE);
        int dataKeyIterations = Tools.rng(MIN_ITERATIONS, MAX_ITERATIONS);

        /* Create the encryption key for the users DataKey */
        SecretKey dataKeyEncryptionKey = new PBEKeyBuilder()
          .setPassword(password)
          .setSalt(dataKeySalt, dataKeyIterations)
          .setKeyLength(KEY_LENGTH)
          .buildSecretKey();

        /* Use DataKeyEncryptionKey to encrypt DataKey before storing */
        byte[] encryptedDataKey = Cipher.encrypt(dataKey.getEncoded(),
          dataKeyIV, dataKeyEncryptionKey, ENCRYPTION_ALGORITHM);

        if(encryptedDataKey.length == 0)
            return false;

        /* Add the user to the database */
        // TODO: Replace with batch query
        int affectedRows = databaseController
          .prepareQuery(ADD_DATA_KEY)
            .setParameter(":data_key", dataKeyIterations + ":" +
              Tools.encodeBase64(dataKeyIV, dataKeySalt, encryptedDataKey))
          .executeUpdate();
        affectedRows += databaseController.prepareQuery(ADD_USER_QUERY)
            .setParameter(":account_uuid", uuid)
            .setParameter(":email", email)
          .executeUpdate();

        /* Clean sensitive data */
        Tools.clean(password);
        Tools.clean(encryptedDataKey);

        return affectedRows == 2;
    }

    /**
     * Authenticate a user with a provided email and password.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return True if the password is correct for the user matching the email,
     * or false otherwise.
     * @throws Exception
     */
    public boolean authenticateUser(String email, char[] password)
      throws EncryptionUtilsException, DBUtilsException {
        if(email == null || password == null)
            return false;
        /* Get the users decrypted data key */
        SecretKey dataKey = getUsersDecryptedDataKey(email, password);
        /* Verify the users password using their data key */
        return dataKey != null;
    }

    /**
     * Get the users encrypted data key, build the encryption key for the data
     * key, then decrypt and return.
     *
     * @param email The email of the user.
     * @param password The password of the user.
     * @return The users decrypted data key, or null if a failure occurs.
     * @throws Exception
     */
    private SecretKey getUsersDecryptedDataKey(String email, char[] password)
      throws EncryptionUtilsException, DBUtilsException {
        /* Get the users encrypted data key */
        EncryptedEntry encryptedDataKey = databaseController
          .prepareQuery(SELECT_DATA_KEY_QUERY)
          .setParameter(":email", email)
          .executeAndMap(resultSet -> new EncryptedEntry(resultSet
            .getString("data_key")));

        if(encryptedDataKey == null)
            return null;

        /* Build the DataKey's encryption key */
        SecretKey dataKeyEncryptionKey = new PBEKeyBuilder()
          .setPassword(password)
          .setSalt(encryptedDataKey.getSalt(), encryptedDataKey.getIterations())
          .setKeyLength(KEY_LENGTH)
          .buildSecretKey();

        /* Decrypt DataKey */
        byte[] decryptedDataKey = Cipher.decrypt(encryptedDataKey.getValue(),
          encryptedDataKey.getIV(), dataKeyEncryptionKey, ENCRYPTION_ALGORITHM);

        /* If decryption fails, treat as invalid credentials */
        if (decryptedDataKey.length == 0)
            return null;

        /* Create SecretKeySpec from decrypted DataKey */
        SecretKey key = new SecretKeySpec(decryptedDataKey, KEY_ALGORITHM);

        /* Clear sensitive data */
        Tools.clean(decryptedDataKey);

        return key;
    }
}
