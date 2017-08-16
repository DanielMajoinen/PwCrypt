package com.majoinen.d.pwcrypt.views.register;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import com.majoinen.d.pwcrypt.PwCrypt;
import com.majoinen.d.pwcrypt.controllers.RegisterController;
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

public class RegisterPresenter extends GluonPresenter<PwCrypt> {

    private static final Logger LOGGER =
      LogManager.getLogger(RegisterPresenter.class);

    @FXML
    private View view;

    @FXML
    private TextField emailInput;

    @FXML
    private Label emailLabel;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private Button register;

    @Inject
    private RegisterController controller;

    public void initialize() {
        controller.setView(this);
        // Hide the AppBar
        view.setOnShowing(event ->
          PwCrypt.getInstance().getAppBar().setVisible(false));

        initTextField(emailInput, emailLabel, emailErrorLabel);
        initTextField(passwordInput, passwordLabel, passwordErrorLabel);
    }

    @FXML
    private void register() {
        controller.register(emailInput.getText(), passwordInput.getText());
    }

    @FXML
    private void gotoLogin() {
        clear(emailInput, emailErrorLabel);
        clear(passwordInput, passwordErrorLabel);
        ViewManager.LOGIN_VIEW.switchView();
    }

    private void initTextField(TextField textField, Label label,
      Label errorLabel) {
        textField.textProperty().addListener((obs, ov, nv) -> {
            InputStyleManager.styleInputNormal(textField, label);
            errorLabel.setText(null);
        });
    }

    public void styleEmailInput(InputStyle style) {
        InputStyleManager.styleInput(emailInput, emailLabel, style);
    }

    public void setEmailErrorLabel(String text) {
        emailErrorLabel.setText(text);
    }

    public void stylePasswordInput(InputStyle style) {
        InputStyleManager.styleInput(passwordInput, passwordLabel, style);
    }

    public void setPasswordErrorLabel(String text) {
        passwordErrorLabel.setText(text);
    }

    private void clear(TextField textField, Label errorLabel) {
        textField.clear();
        errorLabel.setText(null);
        InputStyleManager.styleInputNormal(textField, errorLabel);
    }
}
