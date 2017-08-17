package com.majoinen.d.pwcrypt.views.credentials;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.majoinen.d.pwcrypt.PwCrypt;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import static com.gluonhq.charm.glisten.afterburner.DefaultDrawerManager
  .DRAWER_LAYER;

/**
 * @author Daniel Majoinen
 * @version 1.0, 18/8/17
 */
public class CredentialsPresenter extends GluonPresenter<PwCrypt> {

    private static final Logger logger =
      LogManager.getLogger(CredentialsPresenter.class);

    @FXML
    private View view;

    @FXML
    private VBox credentialsVBox;

    public void initialize() {
        view.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                  MobileApplication.getInstance().showLayer(DRAWER_LAYER)));
                appBar.setTitleText("Credentials");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(
                  event -> logger.info("Search credentials")));
                appBar.getActionItems().add(MaterialDesignIcon.ADD.button(
                  event -> logger.info("Add credential")));
            }
        });
    }
}
