package com.majoinen.d.pwcrypt.views.login;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import javafx.fxml.FXML;

public class LoginPresenter {

    private static final Logger logger =
      LogManager.getLogger(LoginPresenter.class);

    @FXML
    private View view;

    public void initialize() {
        // Hide the AppBar
        view.setOnShowing(event ->
          MobileApplication.getInstance().getAppBar().setVisible(false)
        );
    }
}
