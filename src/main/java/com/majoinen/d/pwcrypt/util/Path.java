package com.majoinen.d.pwcrypt.util;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.StorageService;
import com.majoinen.d.pwcrypt.exception.PwCryptException;

import java.io.IOException;

/**
 * @author Daniel Majoinen
 * @version 1.0, 15/8/17
 */
public class Path {
    public static class File {

        private static final String DESKTOP_PROJECT_FOLDER = ".pwcrypt";

        private static final String DEVICE_UUID_FILE = "/device.uuid";

        private static String privateStorage;

        public static String deviceUuid() throws PwCryptException {
            return privateStorage() + DEVICE_UUID_FILE;
        }

        public static String privateStorage() throws PwCryptException {
            if(privateStorage == null) {
                privateStorage = Services.get(StorageService.class)
                  .map(s -> s.getPrivateStorage().get())
                  .orElseThrow(() -> new PwCryptException("Error: " +
                    "Private storage not available")).getAbsolutePath();
            }
            if(Platform.isDesktop()) {
                privateStorage = privateStorage
                  .replaceAll(".gluon", DESKTOP_PROJECT_FOLDER);
                if(!FileUtils.fileExists(privateStorage))
                    createProjectFolder();
            }
            return privateStorage;
        }

        private static void createProjectFolder() throws PwCryptException {
            try {
               FileUtils.createDirectory(privateStorage);
            } catch(IOException e) {
                throw new PwCryptException("Error creating project folder");
            }
        }
    }
}
