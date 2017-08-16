package com.majoinen.d.pwcrypt;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import com.majoinen.d.pwcrypt.util.DeviceUUIDInitialiser;
import com.majoinen.d.pwcrypt.util.Path;
import com.majoinen.d.pwcrypt.views.ViewManager;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PwCrypt extends MobileApplication {

    private static final Logger logger =
      LogManager.getLogger(PwCrypt.class);

    private static final int WINDOW_MIN_WIDTH = 350;

    private static final int WINDOW_MIN_HEIGHT = 600;

    // Name of the global css file
    private static final String GLOBAL_CSS = "global.css";

    // Name of the icon file
    private static final String ICON = "/icon.png";

    // Device UUID
    private static String deviceUuid;

    public static String getDeviceUUID() {
        return deviceUuid;
    }

    @Override
    public void init() throws Exception {
        logger.debug("Private Storage: " + Path.File.privateStorage());
        deviceUuid = DeviceUUIDInitialiser.initDeviceUUID();
        ViewManager.initViews(this);
        ViewManager.initLayers(this);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        ((Stage) scene.getWindow()).setMinWidth(WINDOW_MIN_WIDTH);
        ((Stage) scene.getWindow()).setMinHeight(WINDOW_MIN_HEIGHT);

        scene.getStylesheets().add(PwCrypt.class.getResource(GLOBAL_CSS)
          .toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(PwCrypt.class
          .getResourceAsStream(ICON)));
    }
}
