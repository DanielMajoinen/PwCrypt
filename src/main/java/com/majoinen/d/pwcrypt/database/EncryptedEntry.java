package com.majoinen.d.pwcrypt.database;

import com.majoinen.d.encryption.utils.Tools;

/**
 * Model class for an encrypted database entry.
 *
 * @author Daniel Majoinen
 * @version 1.0, 27/7/17
 */
public class EncryptedEntry {

    // Define the delimiter which splits the provided string
    private static final String DELIMITER = ":";

    // Set the index for the iteration count
    private static final int ITERATIONS_INDEX = 0;

    // Set the index for the initialization vector
    private static final int INIT_VECTOR_INDEX = 1;

    // Set the index for the salt
    private static final int SALT_INDEX = 2;

    // Set the index for the encrypted message
    private static final int MESSAGE_INDEX = 3;

    private int iterations;
    private byte[] iv;
    private byte[] salt;
    private byte[] value;

    public EncryptedEntry(String input) throws IllegalArgumentException {
        String[] splitValue = input.split(DELIMITER);
        if(splitValue.length != 4)
            throw new IllegalArgumentException("[EncryptionUtils] " +
              "Input must split into 4 parts: Iterations, IV, Salt, Value.");
        this.iterations = Integer.parseInt(splitValue[ITERATIONS_INDEX]);
        this.iv = Tools.decodeBase64(splitValue[INIT_VECTOR_INDEX]);
        this.salt = Tools.decodeBase64(splitValue[SALT_INDEX]);
        this.value = Tools.decodeBase64(splitValue[MESSAGE_INDEX]);
    }

    /**
     * Getter for the iteration count.
     * @return the iteration count.
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * Getter for the salt.
     * @return the salt.
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * Getter for the initialization vector.
     * @return the initialization vector.
     */
    public byte[] getIV() {
        return iv;
    }

    /**
     * Getter for the encrypted message.
     * @return The value of the encrypted entry.
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * Cleans all sensitive data.
     */
    public void clean() {
        iterations = 0;
        Tools.clean(iv, salt, value);
    }
}
