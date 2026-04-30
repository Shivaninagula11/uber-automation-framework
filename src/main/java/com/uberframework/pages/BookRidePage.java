package com.uberframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BookRidePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By confirmRideButton = By.xpath(
        "//button[contains(text(),'Confirm')]");
    private By cancelRideButton = By.xpath(
        "//button[contains(text(),'Cancel ride')]");
    private By rideStatusText = By.xpath(
        "//div[contains(@data-testid,'ride-status')]");
    private By driverName = By.xpath(
        "//div[contains(@data-testid,'driver-name')]");
    private By driverRating = By.xpath(
        "//div[contains(@data-testid,'driver-rating')]");
    private By vehicleDetails = By.xpath(
        "//div[contains(@data-testid,'vehicle-details')]");
    private By etaText = By.xpath(
        "//div[contains(@data-testid,'eta')]");
    private By sosButton = By.xpath(
        "//button[contains(@data-testid,'sos')]");
    private By tripFare = By.xpath(
        "//div[contains(@data-testid,'trip-fare')]");
    private By scheduleDate = By.xpath(
        "//input[contains(@placeholder,'Date')]");
    private By scheduleTime = By.xpath(
        "//input[contains(@placeholder,'Time')]");
    private By confirmSchedule = By.xpath(
        "//button[contains(text(),'Confirm Schedule')]");
    private By cancelConfirmButton = By.xpath(
        "//button[contains(text(),'Yes, cancel')]");

    public BookRidePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,
            Duration.ofSeconds(20));
    }

    // Confirm ride booking
    public void confirmRide() {
        wait.until(ExpectedConditions
            .elementToBeClickable(confirmRideButton))
            .click();
    }

    // Cancel ride
    public void cancelRide() {
        wait.until(ExpectedConditions
            .elementToBeClickable(cancelRideButton))
            .click();
        // Confirm cancellation popup
        wait.until(ExpectedConditions
            .elementToBeClickable(cancelConfirmButton))
            .click();
    }

    // Get ride status
    public String getRideStatus() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(rideStatusText))
            .getText();
    }

    // Get driver name
    public String getDriverName() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(driverName))
            .getText();
    }

    // Get driver rating
    public String getDriverRating() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(driverRating))
            .getText();
    }

    // Get vehicle details
    public String getVehicleDetails() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(vehicleDetails))
            .getText();
    }

    // Get ETA
    public String getETA() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(etaText))
            .getText();
    }

    // Check SOS button visible
    public boolean isSOSButtonVisible() {
        try {
            wait.until(ExpectedConditions
                .visibilityOfElementLocated(sosButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get trip fare
    public String getTripFare() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(tripFare))
            .getText();
    }

    // Schedule ride
    public void scheduleRide(String date, String time) {
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(scheduleDate))
            .sendKeys(date);
        wait.until(ExpectedConditions
            .visibilityOfElementLocated(scheduleTime))
            .sendKeys(time);
        wait.until(ExpectedConditions
            .elementToBeClickable(confirmSchedule))
            .click();
    }

    // Check ride confirmed
    public boolean isRideConfirmed() {
        try {
            String status = getRideStatus();
            return status.contains("Looking") ||
                   status.contains("Arriving") ||
                   status.contains("On Trip");
        } catch (Exception e) {
            return false;
        }
    }
}