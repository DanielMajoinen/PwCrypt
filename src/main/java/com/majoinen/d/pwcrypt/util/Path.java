package com.majoinen.d.pwcrypt.util;

import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.StorageService;
import com.majoinen.d.pwcrypt.exception.PwCryptException;

/**
 * @author Daniel Majoinen
 * @version 1.0, 15/8/17
 */
public class Path {
    public static class File {
        public static String privateStorage() throws PwCryptException {
            return Services.get(StorageService.class)
              .map(s -> s.getPrivateStorage().get())
              .orElseThrow(() -> new PwCryptException("Error: " +
                "Private storage not available")).getAbsolutePath();
        }
    }
}
