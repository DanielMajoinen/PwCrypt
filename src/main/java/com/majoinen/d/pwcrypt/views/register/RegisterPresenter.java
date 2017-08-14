package com.majoinen.d.pwcrypt.views.register;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXML;

public class RegisterPresenter {

    @FXML
    private View view;

    public void initialize() {
        // Hide the AppBar
        view.setOnShowing(event ->
          MobileApplication.getInstance().getAppBar().setVisible(false)
        );
    }
}
