package com.uberframework.utils;

import com.uberframework.config.ConfigReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, 
                                           String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
            .format(new Date());

        String screenshotPath = ConfigReader.getScreenshotPath()
            + testName + "_" + timestamp + ".png";

        try {
            File source = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);
            File destination = new File(screenshotPath);
            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot saved: " 
                + screenshotPath);
        } catch (IOException e) {
            System.out.println("Screenshot failed: " 
                + e.getMessage());
        }

        return screenshotPath;
    }
}