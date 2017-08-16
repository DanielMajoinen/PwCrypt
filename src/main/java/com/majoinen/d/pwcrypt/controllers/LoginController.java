package com.majoinen.d.pwcrypt.controllers;

import com.majoinen.d.pwcrypt.input.InputStyle;
import com.majoinen.d.pwcrypt.views.login.LoginPresenter;

/**
 * @author Daniel Majoinen
 * @version 1.0, 16/8/17
 */
public class LoginController {

    private LoginPresenter view;

    public void setView(LoginPresenter view) {
        this.view = view;
    }

    public void login(String email, String password) {
        view.styleEmailInput(InputStyle.ERROR);
        view.stylePasswordInput(InputStyle.ERROR);
        view.styleLoginButton(InputStyle.ERROR);
        view.setButtonText("Incorrect credentials!");
    }
}
