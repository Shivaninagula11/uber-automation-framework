package com.uberframework.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CookieUtils {

    private static final String SID_VALUE
        = "g.a0009QiBbTNhNuLbNGbznJmVjL0EEmBx7hrkLJmkjZovUbKvdAn5-bAEVgdtLXg043PYUFz5oQACgYKAeQSARISFQHGX2MiySgwbSec7rrxPiVNQC-_GxoVAUF8yKqy-LyHJKo4JeiS9BbXz2da0076";

    private static final String CSID_VALUE
        = "1.1780192164968.Eu4mAzYkujWkaqh/LArG+O8RoYDMsKDHm9OSMjYQ9D4=";

    public static void injectUberCookies(
            WebDriver driver) {

        // Step 1: Go to Uber first
        driver.get("https://www.uber.com");

        // Step 2: Delete all existing cookies
        driver.manage().deleteAllCookies();

        // Step 3: Add sid cookie
        Cookie sid = new Cookie.Builder(
            "sid", SID_VALUE)
            .domain(".uber.com")
            .path("/")
            .isSecure(true)
            .build();

        // Step 4: Add csid cookie
        Cookie csid = new Cookie.Builder(
            "csid", CSID_VALUE)
            .domain(".uber.com")
            .path("/")
            .isSecure(true)
            .build();

        driver.manage().addCookie(sid);
        driver.manage().addCookie(csid);

        // Step 5: Refresh page
        driver.navigate().refresh();

        // Step 6: Wait for page to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Step 7: Print current URL
        System.out.println("Current URL: " 
            + driver.getCurrentUrl());
        System.out.println("Page Title: " 
            + driver.getTitle());

        System.out.println(
            "Cookies injected successfully!");
    }

    public static boolean isLoggedIn(
            WebDriver driver) {
        try {
            // Check URL - if logged in
            // Uber redirects away from login page
            String currentUrl = 
                driver.getCurrentUrl();
            String pageSource = 
                driver.getPageSource();

            System.out.println(
                "Checking login - URL: " 
                + currentUrl);

            // Check if we are NOT on login page
            boolean notOnLoginPage = 
                !currentUrl.contains("auth.uber.com")
                && !currentUrl.contains("login");

            // Check if page has user elements
            boolean hasUserElements = 
                pageSource.contains("Log out") ||
                pageSource.contains("My trips") ||
                pageSource.contains("Account") ||
                pageSource.contains("profile");

            System.out.println(
                "Not on login page: " 
                + notOnLoginPage);
            System.out.println(
                "Has user elements: " 
                + hasUserElements);

            return notOnLoginPage 
                || hasUserElements;

        } catch (Exception e) {
            System.out.println(
                "Login check error: " 
                + e.getMessage());
            return false;
        }
    }
}