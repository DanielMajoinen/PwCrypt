package com.majoinen.d.pwcrypt.log;

/**
 * @author Daniel Majoinen
 * @version 1.0, 14/8/17
 */
public interface Logger {
    void info(String message);
    void debug(String message);
    void error(String message);
    void error(String message, Throwable throwable);
    void fatal(String message);
    void fatal(String message, Throwable throwable);
}
