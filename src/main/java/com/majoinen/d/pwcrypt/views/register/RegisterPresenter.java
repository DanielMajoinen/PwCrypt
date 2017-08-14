package com.majoinen.d.pwcrypt.views.register;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import com.majoinen.d.pwcrypt.PwCrypt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterPresenter {

    @FXML
    private View view;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Button register;

    public void initialize() {
        // Hide the AppBar
        view.setOnShowing(event -> {
          MobileApplication.getInstance().getAppBar().setVisible(false);
        });
    }

    @FXML
    private void gotoLogin() {
        MobileApplication.getInstance().switchView(PwCrypt.LOGIN_VIEW);
    }
}
