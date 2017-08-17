package com.majoinen.d.pwcrypt.controllers;

import com.majoinen.d.database.DatabaseController;
import com.majoinen.d.database.exception.DBUtilsException;
import com.majoinen.d.encryption.exception.EncryptionUtilsException;
import com.majoinen.d.pwcrypt.database.AccountDao;
import com.majoinen.d.pwcrypt.database.DatabaseManager;
import com.majoinen.d.pwcrypt.exception.PwCryptException;
import com.majoinen.d.pwcrypt.input.InputStyle;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import com.majoinen.d.pwcrypt.util.Path;
import com.majoinen.d.pwcrypt.views.ViewManager;
import com.majoinen.d.pwcrypt.views.login.LoginPresenter;

/**
 * @author Daniel Majoinen
 * @version 1.0, 16/8/17
 */
public class LoginController {

    private static final Logger LOGGER =
      LogManager.getLogger(LoginController.class);

    private LoginPresenter view;

    public void setView(LoginPresenter view) {
        this.view = view;
    }

    public void login(String email, String password) {
        DatabaseController databaseController;
        try {
            databaseController = DatabaseManager.getController(email);
            databaseController.setProperty("root.directory",
              Path.File.privateStorage());
            if(!databaseController.databaseExists()) {
                LOGGER.debug("Database does not exist");
                failedLogin();
            } else {
                attemptLogin(email, password, AccountDao.getInstance(
                  databaseController));
            }
        } catch(DBUtilsException | PwCryptException e) {
            LOGGER.error("Error logging in", e);
            failedLogin(); // TODO: Change to error alert
        }
    }

    private void attemptLogin(String email, String password,
      AccountDao accountDao) {
        LOGGER.info("Attempting login");
        try {
            if(accountDao.authenticateUser(email, password.toCharArray()))
                successfulLogin();
            else {
                LOGGER.info("Incorrect credentials");
                failedLogin();
            }
        } catch(DBUtilsException | EncryptionUtilsException e) {
            LOGGER.error("Error attempting login", e);
        }
    }

    private void successfulLogin() {
        LOGGER.info("Successful login");
        ViewManager.CREDENTIALS_VIEW.switchView();
    }

    private void failedLogin() {
        view.styleEmailInput(InputStyle.ERROR);
        view.stylePasswordInput(InputStyle.ERROR);
        view.styleLoginButton(InputStyle.ERROR);
        view.setButtonText("Incorrect credentials!");
    }
}
