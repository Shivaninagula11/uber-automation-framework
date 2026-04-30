package com.uberframework.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = 
        "src/main/resources/config.properties";

    static {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_PATH);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(
                "config.properties not found at: " + CONFIG_PATH);
        }
    }

    public static String getBrowser() {
        return properties.getProperty("browser");
    }

    public static String getUrl() {
        return properties.getProperty("url");
    }

    public static String getEmail() {
        return properties.getProperty("email");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(
            properties.getProperty("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(
            properties.getProperty("explicit.wait"));
    }

    public static String getScreenshotPath() {
        return properties.getProperty("screenshot.path");
    }

    public static String getReportPath() {
        return properties.getProperty("report.path");
    }

    public static String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public static String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public static String getDbPassword() {
        return properties.getProperty("db.password");
    }

    public static String getApiBaseUrl() {
        return properties.getProperty("api.base.url");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(
            properties.getProperty("headless"));
    }
}