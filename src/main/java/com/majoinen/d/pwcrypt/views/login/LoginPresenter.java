package com.majoinen.d.pwcrypt.views.login;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import com.majoinen.d.pwcrypt.PwCrypt;
import com.majoinen.d.pwcrypt.controllers.LoginController;
import com.majoinen.d.pwcrypt.input.InputStyle;
import com.majoinen.d.pwcrypt.input.InputStyleManager;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import com.majoinen.d.pwcrypt.views.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label emailLabel;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button loginButton;

    @Inject
    private LoginController controller;

    public void initialize() {
        controller.setView(this);
        // Hide the AppBar & remove focus from email box
        view.setOnShowing(event -> {
            PwCrypt.getInstance().getAppBar().setVisible(false);
            view.requestFocus();
        });

        setOnChange(emailInput, emailLabel);
        setOnChange(passwordInput, passwordLabel);
    }

    @FXML
    private void login() {
        controller.login(emailInput.getText(), passwordInput.getText());
    }

    @FXML
    private void gotoRegister() {
        clear();
        ViewManager.REGISTER_VIEW.switchView();
    }

    public void styleLoginButton(InputStyle style) {
        InputStyleManager.styleInput(loginButton, style);
    }

    public void setButtonText(String text) {
        loginButton.setText(text);
    }

    public void styleEmailInput(InputStyle style) {
        InputStyleManager.styleInput(emailInput, emailLabel, style);
    }

    public void stylePasswordInput(InputStyle style) {
        InputStyleManager.styleInput(passwordInput, passwordLabel, style);
    }

    private void setOnChange(TextField textField, Label label) {
        textField.textProperty().addListener((obs, ov, nv) -> {
            InputStyleManager.styleInputNormal(emailInput, emailLabel);
            InputStyleManager.styleInputNormal(passwordInput, passwordLabel);
            InputStyleManager.styleInputNormal(loginButton);
            setButtonText("Login");
        });
    }

    private void clear() {
        emailInput.clear();
        passwordInput.clear();
    }
}
