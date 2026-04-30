package com.uberframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By paymentMenu = By.xpath(
        "//a[contains(@href,'payment')]");
    private By addPaymentButton = By.xpath(
        "//button[contains(text(),'Add Payment')]");
    private By cardNumberField = By.xpath(
        "//input[contains(@placeholder,'Card number')]");
    private By expiryField = By.xpath(
        "//input[contains(@placeholder,'MM/YY')]");
    private By cvvField = By.xpath(
        "//input[contains(@placeholder,'CVV')]");
    private By cardNameField = By.xpath(
        "//input[contains(@placeholder,'Name on card')]");
    private By saveCardButton = By.xpath(
        "//button[contains(text(),'Save')]");
    private By upiField = By.xpath(
        "//input[contains(@placeholder,'UPI ID')]");
    private By verifyUpiButton = By.xpath(
        "//button[contains(text(),'Verify')]");
    private By promoCodeField = By.xpath(
        "//input[contains(@placeholder,'Promo code')]");
    private By applyPromoButton = By.xpath(
        "//button[contains(text(),'Apply')]");
    private By promoSuccessMsg = By.xpath(
        "//div[contains(@class,'promo-success')]");
    private By promoErrorMsg = By.xpath(
        "//div[contains(@class,'promo-error')]");
    private By walletBalance = By.xpath(
        "//div[contains(@data-testid,'wallet-balance')]");
    private By fareBreakdown = By.xpath(
        "//div[contains(@data-testid,'fare-breakdown')]");
    private By baseFare = By.xpath(
        "//div[contains(@data-testid,'base-fare')]");
    private By taxes = By.xpath(
        "//div[contains(@data-testid,'taxes')]");
    private By totalFare = By.xpath(
        "//div[contains(@data-testid,'total-fare')]");
    private By paymentSuccess = By.xpath(
        "//div[contains(@data-testid,'payment-success')]");

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
            Duration.ofSeconds(20));
    }

    // Click payment menu
    public void clickPaymentMenu() {
        wait.until(ExpectedConditions
            .elementToBeClickable(paymentMenu)).click();
    }

    // Add credit/debit card
    public void addCard(String cardNumber, String expiry,
                        String cvv, String name) {
        wait.until(ExpectedConditions
            .elementToBeClickable(addPaymentButton)).click();

        wait.until(ExpectedConditions
            .visibilityOfElementLocated(cardNumberField))
            .sendKeys(cardNumber);

        driver.findElement(expiryField).sendKeys(expiry);
        driver.findElement(cvvField).sendKeys(cvv);
        driver.findElement(cardNameField).sendKeys(name);

        wait.until(ExpectedConditions
            .elementToBeClickable(saveCardButton)).click();
    }

    // Add UPI
    public void addUPI(String upiId) {
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(upiField))
            .sendKeys(upiId);
        wait.until(ExpectedConditions
            .elementToBeClickable(verifyUpiButton)).click();
    }

    // Apply promo code
    public void applyPromoCode(String code) {
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(promoCodeField))
            .sendKeys(code);
        wait.until(ExpectedConditions
            .elementToBeClickable(applyPromoButton)).click();
    }

    // Check promo code success
    public boolean isPromoApplied() {
        try {
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(promoSuccessMsg));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get promo error message
    public String getPromoErrorMessage() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(promoErrorMsg))
            .getText();
    }

    // Get wallet balance
    public String getWalletBalance() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(walletBalance))
            .getText();
    }

    // Get base fare
    public String getBaseFare() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(baseFare))
            .getText();
    }

    // Get taxes
    public String getTaxes() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(taxes))
            .getText();
    }

    // Get total fare
    public String getTotalFare() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(totalFare))
            .getText();
    }

    // Check payment successful
    public boolean isPaymentSuccessful() {
        try {
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(paymentSuccess));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}