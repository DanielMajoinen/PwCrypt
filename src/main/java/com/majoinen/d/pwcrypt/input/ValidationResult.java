package com.majoinen.d.pwcrypt.input;

/**
 * @author Daniel Majoinen
 * @version 1.0, 16/8/17
 */
public class ValidationResult {

    private final boolean result;

    private final String reason;

    public ValidationResult(boolean result) {
        this.result = result;
        this.reason = "";
    }

    public ValidationResult(boolean result, String reason) {
        this.result = result;
        this.reason = reason;
    }

    public boolean isValid() {
        return result;
    }

    public String getReason() {
        return reason;
    }
}
