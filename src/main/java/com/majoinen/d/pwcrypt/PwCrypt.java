package com.majoinen.d.pwcrypt;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.majoinen.d.pwcrypt.exception.PwCryptException;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import com.majoinen.d.pwcrypt.util.FileUtils;
import com.majoinen.d.pwcrypt.util.Path;
import com.majoinen.d.pwcrypt.views.ViewManager;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PwCrypt extends MobileApplication {

    private static final Logger logger =
      LogManager.getLogger(PwCrypt.class);

    // Name of the global css file
    private static final String GLOBAL_CSS = "global.css";

    // Name of the icon file
    private static final String ICON = "/icon.png";

    // Device UUID
    private static String deviceUuid;

    @Override
    public void init() throws Exception {
        logger.debug("Private Storage: " + Path.File.privateStorage());
        initDeviceUUID();
        ViewManager.initViews(this);
        ViewManager.initLayers(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(PwCrypt.class.getResource(GLOBAL_CSS)
          .toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(PwCrypt.class
          .getResourceAsStream(ICON)));
    }

    public static String getDeviceUUID() {
        return deviceUuid;
    }

    private static void initDeviceUUID() throws PwCryptException {
        String path = Path.File.deviceUuid();
        if (deviceUuid == null) {
            if(FileUtils.fileExists(path))
                deviceUuid = readDeviceUUIDFile();
            else
                deviceUuid = createDeviceUUIDFile();
        }
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
