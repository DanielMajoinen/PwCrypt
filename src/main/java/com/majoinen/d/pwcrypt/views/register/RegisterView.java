package com.majoinen.d.pwcrypt.views.register;

import com.gluonhq.charm.glisten.mvc.View;
import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class RegisterView {

    private static final Logger logger =
      LogManager.getLogger(RegisterView.class);

    private static final String FXML = "register.fxml";

    private final String name;

    public RegisterView(String name) {
        this.name = name;
    }

    public View getView() {
        try {
            View view = FXMLLoader.load(RegisterView.class.getResource(FXML));
            view.setName(name);
            return view;
        } catch (IOException e) {
            logger.error("IOException getting " + FXML, e);
            return new View(name);
        }
    }
}
