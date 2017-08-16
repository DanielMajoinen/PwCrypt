package com.majoinen.d.pwcrypt.views;

import javafx.scene.control.TextField;

/**
 * @author Daniel Majoinen
 * @version 1.0, 16/8/17
 */
public class StyleManager {

    private StyleManager() { }

    public static void styleInputNormal(TextField textField) {
        textField.getStyleClass().remove("pwcrypt-input-error");
        textField.getStyleClass().add("pwcrypt-input");
    }

    public static void styleInputError(TextField textField) {
        textField.getStyleClass().remove("pwcrypt-input");
        textField.getStyleClass().add("pwcrypt-input-error");
    }
}
