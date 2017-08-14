package com.majoinen.d.pwcrypt;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.majoinen.d.pwcrypt.views.login.LoginView;
import com.majoinen.d.pwcrypt.views.register.RegisterView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PwCrypt extends MobileApplication {

    // Name of the global css file
    private static final String GLOBAL_CSS = "global.css";

    // Name of the icon file
    private static final String ICON = "/icon.png";

    // Views
    public static final String LOGIN_VIEW = HOME_VIEW;
    public static final String REGISTER_VIEW = "Register View";
    public static final String MENU_LAYER = "Side Menu";

    @Override
    public void init() {
        // Add views
        addViewFactory(LOGIN_VIEW, () ->
          new LoginView(LOGIN_VIEW).getView());
        addViewFactory(REGISTER_VIEW, () ->
          new RegisterView(REGISTER_VIEW).getView());

        // Add layers
        addLayerFactory(MENU_LAYER, () ->
          new SidePopupView(new DrawerManager().getDrawer()));
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
