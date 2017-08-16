package com.majoinen.d.pwcrypt.controllers;

import com.majoinen.d.database.DatabaseController;
import com.majoinen.d.database.DatabaseControllerFactory;
import com.majoinen.d.database.exception.DBUtilsException;
import com.majoinen.d.encryption.exception.EncryptionUtilsException;
import com.majoinen.d.pwcrypt.PwCrypt;
import com.majoinen.d.pwcrypt.database.AccountDao;
import com.majoinen.d.pwcrypt.exception.PwCryptException;
import com.majoinen.d.pwcrypt.input.EmailValidator;
import com.majoinen.d.pwcrypt.input.InputStyle;
import com.majoinen.d.pwcrypt.input.ValidationResult;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import com.majoinen.d.pwcrypt.util.Path;
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
        if(validateEmail(email) & validatePassword(password)) {
            try {
                DatabaseController databaseController =
                  DatabaseControllerFactory.getController(email,
                    PwCrypt.DATABASE_CONFIG);
                databaseController.setProperty("root.directory",
                  Path.File.privateStorage());
                if(!databaseController.databaseExists())
                    initNewAccount(email, password, databaseController);
                else
                    accountAlreadyExists();
            } catch(DBUtilsException e) {
                LOGGER.error("Error checking for database file", e);
            } catch(PwCryptException e) {
                LOGGER.error("Error setting database root directory", e);
            }
        }
    }

    private void initNewAccount(String email, String password,
      DatabaseController databaseController) {
        try {
            databaseController.init();
            if(AccountDao.getInstance(databaseController)
              .addNewUser(email, password.toCharArray())) {
                LOGGER.info("Registration successful");
                PwCrypt.getInstance().showMessage("Successfully registered!");
            }
        } catch(DBUtilsException | EncryptionUtilsException e) {
            LOGGER.error("Error creating new account", e);
        }
    }

    private void accountAlreadyExists() {
        view.styleEmailInput(InputStyle.ERROR);
        view.stylePasswordInput(InputStyle.ERROR);
        view.setEmailErrorLabel("Email already in use");
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
