package com.majoinen.d.pwcrypt.util;

import com.majoinen.d.pwcrypt.exception.PwCryptException;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Daniel Majoinen
 * @version 1.0, 16/8/17
 */
public class DeviceUUIDInitialiser {

    private static final Logger logger =
      LogManager.getLogger(DeviceUUIDInitialiser.class);

    private DeviceUUIDInitialiser() { }

    public static String initDeviceUUID() throws PwCryptException {
        String path = Path.File.deviceUuid();
            if(FileUtils.fileExists(path))
                return readDeviceUUIDFile();
            else
                return createDeviceUUIDFile();
    }

    private static String readDeviceUUIDFile() throws PwCryptException {
        String path = Path.File.deviceUuid();
        File file = new File(path);
        try {
            String uuid = FileUtils.fileToString(file, true);
            logger.debug("device.uuid: " + uuid);
            return uuid;
        } catch(IOException e) {
            throw new PwCryptException("Error reading device.uuid");
        }
    }

    private static String createDeviceUUIDFile() throws PwCryptException {
        String path = Path.File.deviceUuid();
        String uuid = UUID.randomUUID().toString();
        try {
            logger.debug("Creating device.uuid >> " + uuid);
            FileUtils.appendToFile(path, uuid);
            return uuid;
        } catch(IOException e) {
            throw new PwCryptException("Error creating device.uuid");
        }
    }

}
