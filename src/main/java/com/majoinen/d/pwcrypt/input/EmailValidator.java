package com.majoinen.d.pwcrypt.input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Daniel Majoinen
 * @version 1.0, 16/8/17
 */
public class EmailValidator {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|" +
      "}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-" +
      "zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    private EmailValidator() { }

    public static ValidationResult validate(String input) {
        if(input.length() == 0)
            return new ValidationResult(false, "Email address is empty");
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(input);
        boolean result = matcher.matches();
        if(result)
            return new ValidationResult(true);
        else
            return new ValidationResult(false, "Invalid email address");
    }
}
