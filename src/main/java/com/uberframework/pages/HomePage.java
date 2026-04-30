package com.uberframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By pickupLocation = By.xpath(
        "//input[contains(@placeholder,'Pickup location')]");
    private By dropLocation = By.xpath(
        "//input[contains(@placeholder,'Dropoff location')]");
    private By seeRidesButton = By.xpath(
        "//button[contains(text(),'See rides')]");
    private By rideOptions = By.xpath(
        "//div[contains(@data-testid,'ride-option')]");
    private By uberGoOption = By.xpath(
        "//div[contains(text(),'UberGo')]");
    private By uberPremierOption = By.xpath(
        "//div[contains(text(),'Premier')]");
    private By uberAutoOption = By.xpath(
        "//div[contains(text(),'Auto')]");
    private By fareEstimate = By.xpath(
        "//div[contains(@data-testid,'fare-estimate')]");
    private By scheduleRide = By.xpath(
        "//button[contains(text(),'Schedule')]");
    private By activityMenu = By.xpath(
        "//a[contains(@href,'activity')]");
    private By promoCodeField = By.xpath(
        "//input[contains(@placeholder,'Promo code')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
            Duration.ofSeconds(20));
    }

    // Enter pickup location
    public void enterPickupLocation(String location) {
        WebElement pickup = wait.until(ExpectedConditions
            .visibilityOfElementLocated(pickupLocation));
        pickup.clear();
        pickup.sendKeys(location);
    }

    // Enter drop location
    public void enterDropLocation(String location) {
        WebElement drop = wait.until(ExpectedConditions
            .visibilityOfElementLocated(dropLocation));
        drop.clear();
        drop.sendKeys(location);
    }

    // Click See Rides
    public void clickSeeRides() {
        wait.until(ExpectedConditions
            .elementToBeClickable(seeRidesButton)).click();
    }

    // Select UberGo
    public void selectUberGo() {
        wait.until(ExpectedConditions
            .elementToBeClickable(uberGoOption)).click();
    }

    // Select Premier
    public void selectUberPremier() {
        wait.until(ExpectedConditions
            .elementToBeClickable(uberPremierOption)).click();
    }

    // Select Auto
    public void selectUberAuto() {
        wait.until(ExpectedConditions
            .elementToBeClickable(uberAutoOption)).click();
    }

    // Get fare estimate
    public String getFareEstimate() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(fareEstimate))
            .getText();
    }

    // Check ride options displayed
    public boolean areRideOptionsDisplayed() {
        try {
            List<WebElement> options = wait.until(
                ExpectedConditions
                .visibilityOfAllElementsLocatedBy(
                    rideOptions));
            return options.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    // Full book ride flow
    public void searchRide(String pickup, String drop) {
        enterPickupLocation(pickup);
        enterDropLocation(drop);
        clickSeeRides();
    }

    // Click schedule ride
    public void clickScheduleRide() {
        wait.until(ExpectedConditions
            .elementToBeClickable(scheduleRide)).click();
    }

    // Click activity menu
    public void clickActivityMenu() {
        wait.until(ExpectedConditions
            .elementToBeClickable(activityMenu)).click();
    }

    // Apply promo code
    public void applyPromoCode(String code) {
        WebElement promo = wait.until(ExpectedConditions
            .visibilityOfElementLocated(promoCodeField));
        promo.clear();
        promo.sendKeys(code);
    }

    // Check if home page loaded
    public boolean isHomePageLoaded() {
        try {
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(pickupLocation));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}