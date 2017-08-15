package com.majoinen.d.pwcrypt;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.majoinen.d.pwcrypt.views.ViewManager;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PwCrypt extends MobileApplication {

    // Name of the global css file
    private static final String GLOBAL_CSS = "global.css";

    // Name of the icon file
    private static final String ICON = "/icon.png";

    @Override
    public void init() {
        ViewManager.initViews();
        ViewManager.initLayers();
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        scene.getStylesheets().add(PwCrypt.class.getResource(GLOBAL_CSS)
          .toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(PwCrypt.class
          .getResourceAsStream(ICON)));
    }
}
