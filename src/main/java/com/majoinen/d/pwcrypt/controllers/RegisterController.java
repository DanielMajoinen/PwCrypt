package com.majoinen.d.pwcrypt.controllers;

import com.majoinen.d.pwcrypt.input.EmailValidator;
import com.majoinen.d.pwcrypt.input.InputStyle;
import com.majoinen.d.pwcrypt.input.ValidationResult;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import com.majoinen.d.pwcrypt.views.register.RegisterPresenter;

/**
 * @author Daniel Majoinen
 * @version 1.0, 16/8/17
 */
public class RegisterController {

    private static final Logger LOGGER =
      LogManager.getLogger(RegisterController.class);

    private RegisterPresenter view;

    public void setView(RegisterPresenter view) {
        this.view = view;
    }

    public void register(String email, String password) {
        if(validateEmail(email) & validatePassword(password))
            LOGGER.info("Registration successful");
    }

    private boolean validateEmail(String email) {
        String text = email.replaceAll("\\s","").toLowerCase();
        ValidationResult result = EmailValidator.validate(text);
        if(!result.isValid()) {
            view.styleEmailInput(InputStyle.ERROR);
            view.setEmailErrorLabel(result.getReason());
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if(password.length() == 0) {
            view.stylePasswordInput(InputStyle.ERROR);
            view.setPasswordErrorLabel("Password is required");
            return false;
        }
        return true;
    }
}
