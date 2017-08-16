package com.majoinen.d.pwcrypt.views.register;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.mvc.View;
import com.majoinen.d.pwcrypt.PwCrypt;
import com.majoinen.d.pwcrypt.input.EmailValidator;
import com.majoinen.d.pwcrypt.views.StyleManager;
import com.majoinen.d.pwcrypt.input.ValidationResult;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import com.majoinen.d.pwcrypt.views.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterPresenter extends GluonPresenter<PwCrypt> {

    private static final Logger logger =
      LogManager.getLogger(RegisterPresenter.class);

    @FXML
    private View view;

    @FXML
    private TextField emailInput;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private Button register;

    public void initialize() {
        // Hide the AppBar
        view.setOnShowing(event ->
          PwCrypt.getInstance().getAppBar().setVisible(false));

        initTextField(emailInput, emailErrorLabel);
        initTextField(passwordInput, passwordErrorLabel);
    }

    @FXML
    private void register() {
        if(validateEmail() & validatePassword()) {
            logger.info("Register success");
        }
    }

    @FXML
    private void gotoLogin() {
        clear(emailInput, emailErrorLabel);
        clear(passwordInput, passwordErrorLabel);
        ViewManager.LOGIN_VIEW.switchView();
    }

    private void initTextField(TextField textField, Label errorLabel) {
        textField.textProperty().addListener((obs, ov, nv) -> {
            StyleManager.styleInputNormal(textField);
            errorLabel.setText(null);
        });
    }

    private void clear(TextField textField, Label errorLabel) {
        textField.clear();
        errorLabel.setText(null);
        StyleManager.styleInputNormal(textField);
    }

    private boolean validateEmail() {
        String email = emailInput.getText().replaceAll("\\s","").toLowerCase();
        ValidationResult result = EmailValidator.validate(email);
        if(!result.isValid()) {
            StyleManager.styleInputError(emailInput);
            emailErrorLabel.setText(result.getReason());
            return false;
        }
        StyleManager.styleInputNormal(emailInput);
        return true;
    }

    private boolean validatePassword() {
        if(passwordInput.getText().length() == 0) {
            StyleManager.styleInputError(passwordInput);
            passwordErrorLabel.setText("Password required");
            return false;
        } else {
            StyleManager.styleInputNormal(passwordInput);
            return true;
        }
    }
}
