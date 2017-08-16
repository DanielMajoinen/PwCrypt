package com.majoinen.d.pwcrypt.controllers;

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
}
