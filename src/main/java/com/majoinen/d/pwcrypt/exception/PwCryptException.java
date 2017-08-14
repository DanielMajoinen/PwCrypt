package com.majoinen.d.pwcrypt.exception;

/**
 * @author Daniel Majoinen
 * @version 1.0, 15/8/17
 */
public class PwCryptException extends Exception {
    public PwCryptException() { }

    public PwCryptException(String message) {
        super(message);
    }

    public PwCryptException(Exception e) {
        super(e);
    }

    public PwCryptException(String message, Exception e) {
        super(message, e);
    }
}
