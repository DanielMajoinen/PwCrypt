package com.majoinen.d.pwcrypt.database;

import com.gluonhq.charm.down.Platform;
import com.majoinen.d.database.DatabaseController;
import com.majoinen.d.database.DatabaseControllerFactory;
import com.majoinen.d.database.exception.DBUtilsException;
import com.majoinen.d.pwcrypt.PwCrypt;
import com.majoinen.d.pwcrypt.exception.PwCryptException;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;

/**
 * @author Daniel Majoinen
 * @version 1.0, 17/8/17
 */
public class DatabaseManager {

    private static final Logger logger =
      LogManager.getLogger(DatabaseManager.class);

    private DatabaseManager() { }

    public static DatabaseController getController(String email)
      throws PwCryptException {
        try {
            return DatabaseControllerFactory.getController(email,
              PwCrypt.DATABASE_CONFIG);
        } catch(DBUtilsException e) {
            throw new PwCryptException("Error getting DatabaseController for " +
              "email: " + email);
        }
    }


    public static void loadJDBCDriver() {
        try {
            Class c = null;
            if (Platform.isAndroid()) {
                c = Class.forName("org.sqldroid.SQLDroidDriver");
            } else if (Platform.isIOS()) {
                c = Class.forName("SQLite.JDBCDriver");
            } else if (Platform.isDesktop()) {
                c = Class.forName("org.sqlite.JDBC");
            } else if (System.getProperty("os.arch").toUpperCase().contains("ARM")) {
                c = Class.forName("org.sqlite.JDBC");
            }
        } catch (ClassNotFoundException e) {
            logger.error("Error class not found " + e);
        }
    }

}
