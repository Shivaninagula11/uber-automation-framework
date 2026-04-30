package com.uberframework.tests;

import com.uberframework.base.BaseTest;
import com.uberframework.config.ConfigReader;
import com.uberframework.pages.BookRidePage;
import com.uberframework.pages.HomePage;
import com.uberframework.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BookRideTest extends BaseTest {

    private LoginPage loginPage;
    private HomePage homePage;
    private BookRidePage bookRidePage;

    @BeforeMethod
    public void setUpPage() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        bookRidePage = new BookRidePage(driver);

        // Login before each test
        loginPage.login(
            ConfigReader.getEmail(),
            ConfigReader.getPassword());
    }

    @Test(priority = 1,
          description = "Verify home page loaded")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Book Ride")
    @Description("Verify home page loads after login")
    public void testHomePageLoaded() {
        Assert.assertTrue(
            homePage.isHomePageLoaded(),
            "Home page did not load!");
    }

    @Test(priority = 2,
          description = "Verify ride search")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Book Ride")
    @Description("Search for a ride with pickup and drop")
    public void testSearchRide() {
        homePage.searchRide(
            "Connaught Place, Delhi",
            "Indira Gandhi Airport, Delhi");
        Assert.assertTrue(
            homePage.areRideOptionsDisplayed(),
            "Ride options not displayed!");
    }

    @Test(priority = 3,
          description = "Verify UberGo selection")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Book Ride")
    @Description("Select UberGo ride type")
    public void testSelectUberGo() {
        homePage.searchRide(
            "Connaught Place, Delhi",
            "Indira Gandhi Airport, Delhi");
        homePage.selectUberGo();
        Assert.assertNotNull(
            homePage.getFareEstimate(),
            "Fare estimate not displayed!");
    }

    @Test(priority = 4,
          description = "Verify UberPremier selection")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Book Ride")
    @Description("Select UberPremier ride type")
    public void testSelectUberPremier() {
        homePage.searchRide(
            "Connaught Place, Delhi",
            "Indira Gandhi Airport, Delhi");
        homePage.selectUberPremier();
        Assert.assertNotNull(
            homePage.getFareEstimate(),
            "Fare estimate not displayed!");
    }

    @Test(priority = 5,
          description = "Verify fare estimate displayed")
    @Severity(SeverityLevel.NORMAL)
    @Story("Book Ride")
    @Description("Verify fare estimate is shown")
    public void testFareEstimateDisplayed() {
        homePage.searchRide(
            "Connaught Place, Delhi",
            "Indira Gandhi Airport, Delhi");
        homePage.selectUberGo();
        String fare = homePage.getFareEstimate();
        Assert.assertTrue(
            fare.contains("₹") || fare.contains("$"),
            "Fare estimate not showing currency!");
    }

    @Test(priority = 6,
          description = "Verify ride confirmation")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Book Ride")
    @Description("Confirm a ride booking")
    public void testConfirmRide() {
        homePage.searchRide(
            "Connaught Place, Delhi",
            "Indira Gandhi Airport, Delhi");
        homePage.selectUberGo();
        bookRidePage.confirmRide();
        Assert.assertTrue(
            bookRidePage.isRideConfirmed(),
            "Ride was not confirmed!");
    }

    @Test(priority = 7,
          description = "Verify ride cancellation")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Book Ride")
    @Description("Cancel a confirmed ride")
    public void testCancelRide() {
        homePage.searchRide(
            "Connaught Place, Delhi",
            "Indira Gandhi Airport, Delhi");
        homePage.selectUberGo();
        bookRidePage.confirmRide();
        bookRidePage.cancelRide();
        Assert.assertFalse(
            bookRidePage.isRideConfirmed(),
            "Ride should have been cancelled!");
    }

    @Test(priority = 8,
          description = "Verify SOS button visible")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Book Ride")
    @Description("Verify SOS button visible during ride")
    public void testSOSButtonVisible() {
        homePage.searchRide(
            "Connaught Place, Delhi",
            "Indira Gandhi Airport, Delhi");
        homePage.selectUberGo();
        bookRidePage.confirmRide();
        Assert.assertTrue(
            bookRidePage.isSOSButtonVisible(),
            "SOS button not visible!");
    }

    @Test(priority = 9,
          description = "Verify schedule ride")
    @Severity(SeverityLevel.NORMAL)
    @Story("Book Ride")
    @Description("Schedule a ride for later")
    public void testScheduleRide() {
        homePage.searchRide(
            "Connaught Place, Delhi",
            "Indira Gandhi Airport, Delhi");
        homePage.clickScheduleRide();
        bookRidePage.scheduleRide(
            "05/15/2026", "10:00 AM");
        Assert.assertTrue(
            bookRidePage.isRideConfirmed(),
            "Scheduled ride not confirmed!");
    }

    @Test(priority = 10,
          dataProvider = "rideData",
          description = "Data driven ride booking")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Book Ride")
    @Description("Data driven test for multiple routes")
    public void testDataDrivenRideBooking(
            String pickup, String drop) {
        homePage.searchRide(pickup, drop);
        Assert.assertTrue(
            homePage.areRideOptionsDisplayed(),
            "Ride options not shown for: "
            + pickup + " to " + drop);
    }

    @DataProvider(name = "rideData")
    public Object[][] getRideData() {
        return new Object[][] {
            {"Connaught Place, Delhi",
             "Indira Gandhi Airport, Delhi"},
            {"Bandra, Mumbai",
             "Chhatrapati Shivaji Airport, Mumbai"},
            {"MG Road, Bangalore",
             "Kempegowda Airport, Bangalore"},
            {"Park Street, Kolkata",
             "Netaji Subhas Airport, Kolkata"}
        };
    }
}