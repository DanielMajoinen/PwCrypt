package com.majoinen.d.pwcrypt.views.login;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import com.majoinen.d.pwcrypt.PwCrypt;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPresenter {

    private static final Logger logger =
      LogManager.getLogger(LoginPresenter.class);

    @FXML
    private View view;

    @FXML
    private Button login;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    public void initialize() {
        // Hide the AppBar & remove focus from email box
        view.setOnShowing(event -> {
            MobileApplication.getInstance().getAppBar().setVisible(false);
            view.requestFocus();
        });
    }

    @FXML
    private void gotoRegister() {
        MobileApplication.getInstance().switchView(PwCrypt.REGISTER_VIEW);
    }
}
