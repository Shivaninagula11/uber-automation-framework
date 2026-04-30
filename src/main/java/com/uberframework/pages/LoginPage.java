package com.uberframework.pages;

import com.uberframework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By signInButton = By.linkText("Sign in");
    private By emailField = By.name("email");
    private By continueButton = By.xpath(
        "//button[contains(text(),'Continue')]");
    private By passwordField = By.name("password");
    private By submitButton = By.xpath(
        "//button[@type='submit']");
    private By errorMessage = By.xpath(
        "//div[contains(@class,'error')]");
    private By profileIcon = By.xpath(
        "//div[contains(@data-testid,'avatar')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 
            Duration.ofSeconds(20));
    }

    // Click Sign In
    public void clickSignIn() {
        wait.until(ExpectedConditions
            .elementToBeClickable(signInButton)).click();
    }

    // Enter Email
    public void enterEmail(String email) {
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(emailField))
            .sendKeys(email);
    }

    // Click Continue
    public void clickContinue() {
        wait.until(ExpectedConditions
            .elementToBeClickable(continueButton)).click();
    }

    // Enter Password
    public void enterPassword(String password) {
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(passwordField))
            .sendKeys(password);
    }

    // Click Submit
    public void clickSubmit() {
        wait.until(ExpectedConditions
            .elementToBeClickable(submitButton)).click();
    }

    // Full Login Flow
    public void login(String email, String password) {
        clickSignIn();
        enterEmail(email);
        clickContinue();
        enterPassword(password);
        clickSubmit();
    }

    // Check if login successful
    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(profileIcon));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get error message
    public String getErrorMessage() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(errorMessage))
            .getText();
    }
}