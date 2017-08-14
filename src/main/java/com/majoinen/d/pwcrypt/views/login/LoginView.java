package com.majoinen.d.pwcrypt.views.login;

import com.gluonhq.charm.glisten.mvc.View;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LoginView {

    private static final Logger logger =
      LogManager.getLogger(LoginView.class);

    private static final String FXML = "login.fxml";

    private final String name;

    public LoginView(String name) {
        this.name = name;
    }

    public View getView() {
        try {
            View view = FXMLLoader.load(LoginView.class.getResource(FXML));
            view.setName(name);
            return view;
        } catch (IOException e) {
            logger.error("IOException getting " + FXML, e);
            return new View(name);
        }
    }
}
