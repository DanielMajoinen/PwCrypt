package com.majoinen.d.pwcrypt.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.majoinen.d.pwcrypt.PwCrypt;
import com.majoinen.d.pwcrypt.views.login.LoginView;
import com.majoinen.d.pwcrypt.views.register.RegisterView;

/**
 * @author Daniel Majoinen
 * @version 1.0, 15/8/17
 */
public class ViewManager {

    /* Views */
    public static final String LOGIN_VIEW = PwCrypt.HOME_VIEW;
    public static final String REGISTER_VIEW = "Register View";

    /* Layers */
    public static final String MENU_LAYER = "Side Menu";

    private ViewManager() { }

    public static void initViews(MobileApplication application) {
        application.addViewFactory(LOGIN_VIEW, () ->
          new LoginView(LOGIN_VIEW).getView());
        application.addViewFactory(REGISTER_VIEW, () ->
          new RegisterView(REGISTER_VIEW).getView());
    }

    public static void initLayers(MobileApplication application) {
        application.addLayerFactory(MENU_LAYER, () ->
          new SidePopupView(new DrawerManager().getDrawer()));
    }
}
