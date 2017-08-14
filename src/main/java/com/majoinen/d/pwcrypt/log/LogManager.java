package com.majoinen.d.pwcrypt.log;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Majoinen
 * @version 1.0, 14/8/17
 */
public class LogManager implements Logger {

    private static final String PREFIX = "[PwCrypt]";

    private Class classType;

    private static Map<Class, LogManager> map;

    private LogManager(Class classType) {
        this.classType = classType;
    }

    public static Logger getLogger(Class classType) {
        if(map == null)
            map = new HashMap<>();
        else if(map.containsKey(classType))
            return map.get(classType);
        LogManager logger = new LogManager(classType);
        map.put(classType, logger);
        return logger;
    }

    @Override
    public void info(String message) {
        output("INFO", message);
    }

    @Override
    public void debug(String message) {
        output("DEBUG", message);
    }

    @Override
    public void error(String message) {
        output("ERROR", message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        output("ERROR", message +"\n"+throwable.getMessage());
        throwable.printStackTrace();
    }

    @Override
    public void fatal(String message) {
        output("FATAL", message);
    }

    @Override
    public void fatal(String message, Throwable throwable) {
        output("FATAL", message +"\n"+throwable.getMessage());
        throwable.printStackTrace();
    }

    private void output(String level, String message) {
        String output = PREFIX
          .concat(" - [")
          .concat(level.toUpperCase())
          .concat("] - ")
          .concat(classType.getSimpleName())
          .concat(" - ")
          .concat(message);
        System.out.println(output);
    }
}
