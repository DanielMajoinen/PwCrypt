package com.majoinen.d.pwcrypt.util;

import com.majoinen.d.pwcrypt.log.LogManager;
import com.majoinen.d.pwcrypt.log.Logger;

import java.io.*;
import java.util.Scanner;

/**
 * @author Daniel Majoinen
 * @version 1.0, 16/8/17
 */
public class FileUtils {

    private static final Logger logger =
      LogManager.getLogger(FileUtils.class);

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean createFile(String path) throws IOException {
        return new File(path).createNewFile();
    }

    public static boolean createDirectory(String path) throws IOException {
        return new File(path).mkdirs();
    }

    public static boolean createIfNotExists(String path) throws IOException {
        return !fileExists(path) || createFile(path);
    }

    public static String fileToString(File file, boolean required)
      throws IOException {
        if(!file.exists() && required) {
            throw new NullPointerException("Missing: "+file.getAbsolutePath());
        } else if(!file.exists()) {
            logger.debug(file.getAbsolutePath()+" is missing");
            return null;
        }
        return fileToString(file);
    }

    private static String fileToString(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            return inputStreamToString(is);
        }
    }

    public static String resourceToString(String path, boolean required)
      throws IOException {
        try (InputStream is = FileUtils.class.getResourceAsStream(path)) {
            if (is == null && required) {
                logger.error("Missing resource file: " + path);
                throw new NullPointerException("resources"+path+" is missing");
            } else if (is == null) {
                return null;
            }
            return inputStreamToString(is);
        }
    }

    private static String inputStreamToString(InputStream inputStream)
      throws IOException {
        String contents = null;
        try (InputStream is = inputStream) {
            Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
            if (scanner.hasNext()) {
                contents = scanner.next();
            }
        }
        return contents;
    }

    public static boolean appendToFile(String fileName, String line)
      throws IOException {
        if(!fileExists(fileName))
            createFile(fileName);
        return appendToFile(new File(fileName), line);
    }

    public static boolean appendToFile(File file, String... line)
      throws IOException {
        PrintWriter printWriter = new PrintWriter(
          new FileOutputStream(file, true));
        for(String l : line)
            printWriter.append(l);
        printWriter.close();
        return true;
    }
}
