package com.majoinen.d.pwcrypt.input;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author Daniel Majoinen
 * @version 1.0, 16/8/17
 */
public class InputStyleManager {

    private InputStyleManager() { }

    public static void styleInput(Button button, InputStyle style) {
        if(style.equals(InputStyle.NORMAL))
            styleInputNormal(button);
        else if(style.equals(InputStyle.ERROR))
            styleInputError(button);
    }

    public static void styleInputNormal(Button button) {
        button.getStyleClass().remove("red-button");
        button.getStyleClass().add("green-button");
    }

    public static void styleInputError(Button button) {
        button.getStyleClass().remove("green-button");
        button.getStyleClass().add("red-button");
    }

    public static void styleInput(TextField textField, Label label,
      InputStyle style) {
        if(style.equals(InputStyle.NORMAL))
            styleInputNormal(textField, label);
        else if(style.equals(InputStyle.ERROR))
            styleInputError(textField, label);
    }

    public static void styleInputNormal(TextField textField, Label label) {
        textField.getStyleClass().remove("pwcrypt-input-error");
        textField.getStyleClass().add("pwcrypt-input");
        label.getStyleClass().remove("pwcrypt-label-error");
        label.getStyleClass().add("pwcrypt-label");
    }

    public static void styleInputError(TextField textField, Label label) {
        textField.getStyleClass().remove("pwcrypt-input");
        textField.getStyleClass().add("pwcrypt-input-error");
        label.getStyleClass().remove("pwcrypt-label");
        label.getStyleClass().add("pwcrypt-label-error");
    }
}
