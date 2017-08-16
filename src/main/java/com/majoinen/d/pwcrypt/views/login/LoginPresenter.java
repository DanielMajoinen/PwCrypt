package com.majoinen.d.pwcrypt.views.login;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import com.majoinen.d.pwcrypt.PwCrypt;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import com.majoinen.d.pwcrypt.views.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class LoginPresenter extends GluonPresenter<PwCrypt> {

    private static final Logger logger =
      LogManager.getLogger(LoginPresenter.class);

    @FXML
    private View view;

    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField passwordInput;

    public void initialize() {
        // Hide the AppBar & remove focus from email box
        view.setOnShowing(event -> {
            PwCrypt.getInstance().getAppBar().setVisible(false);
            view.requestFocus();
        });
    }

    @FXML
    private void login() {

    }

    @FXML
    private void gotoRegister() {
        ViewManager.REGISTER_VIEW.switchView();
    }
}
