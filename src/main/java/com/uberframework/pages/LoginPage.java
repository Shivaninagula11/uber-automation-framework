package com.uberframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By emailOrPhoneField = By.id(
        "PHONE_NUMBER_or_EMAIL_ADDRESS");
    private By continueButton = By.id(
        "forward-button");
    private By errorMessage = By.id(
        "field-error");
    private By googleLoginButton = By.id(
        "google-login-btn");
    private By appleLoginButton = By.id(
        "apple-login-btn");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
            Duration.ofSeconds(20));
    }

    public void enterEmailOrPhone(String value) {
        WebElement field = wait.until(
            ExpectedConditions
            .visibilityOfElementLocated(
                emailOrPhoneField));
        field.clear();
        field.sendKeys(value);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions
            .elementToBeClickable(continueButton))
            .click();
    }

    public void enterCredentialAndContinue(
            String emailOrPhone) {
        enterEmailOrPhone(emailOrPhone);
        clickContinue();
    }

    public boolean isContinueButtonDisplayed() {
        try {
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                    continueButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            return wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                    errorMessage))
                .getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isErrorDisplayed() {
        try {
            String error = getErrorMessage();
            return error != null
                && !error.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginPageLoaded() {
        try {
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                    emailOrPhoneField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickGoogleLogin() {
        wait.until(ExpectedConditions
            .elementToBeClickable(
                googleLoginButton)).click();
    }
}