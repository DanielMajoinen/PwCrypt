package com.majoinen.d.pwcrypt.views;

import com.gluonhq.charm.glisten.afterburner.AppView;
import com.gluonhq.charm.glisten.afterburner.AppViewRegistry;
import com.gluonhq.charm.glisten.afterburner.DefaultDrawerManager;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.majoinen.d.pwcrypt.PwCrypt;
import com.majoinen.d.pwcrypt.views.credentials.CredentialsPresenter;
import com.majoinen.d.pwcrypt.views.login.LoginPresenter;
import com.majoinen.d.pwcrypt.views.register.RegisterPresenter;
import javafx.scene.image.Image;

import java.util.Locale;

import static com.gluonhq.charm.glisten.afterburner.AppView.Flag.*;

public class ViewManager {

    public static final AppViewRegistry REGISTRY = new AppViewRegistry();

    public static final AppView LOGIN_VIEW = view("Login", LoginPresenter.class, MaterialDesignIcon.HOME, HOME_VIEW, SKIP_VIEW_STACK);

    public static final AppView REGISTER_VIEW = view("Register", RegisterPresenter.class, MaterialDesignIcon.DASHBOARD);

    public static final AppView CREDENTIALS_VIEW = view("Credentials", CredentialsPresenter.class, MaterialDesignIcon.LOCK, SHOW_IN_DRAWER);

    private static AppView view(String title, Class<? extends GluonPresenter<?>> presenterClass, MaterialDesignIcon menuIcon, AppView.Flag... flags ) {
        return REGISTRY.createView(name(presenterClass), title, presenterClass, menuIcon, flags);
    }

    private static String name(Class<? extends GluonPresenter<?>> presenterClass) {
        return presenterClass.getSimpleName().toUpperCase(Locale.ROOT).replace("PRESENTER", "");
    }

    public static void registerViewsAndDrawer(MobileApplication app) {
        for (AppView view : REGISTRY.getViews())
            view.registerView(app);

        NavigationDrawer.Header header = new NavigationDrawer.Header("PwCrypt", "Password Manager", new Avatar(21, new Image(PwCrypt.class.getResourceAsStream("/icon.png"))));

        DefaultDrawerManager drawerManager = new DefaultDrawerManager(app, header, REGISTRY.getViews());

        drawerManager.installDrawer();
    }
}
