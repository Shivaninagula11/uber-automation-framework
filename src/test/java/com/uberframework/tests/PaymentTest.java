package com.uberframework.tests;

import com.uberframework.base.BaseTest;
import com.uberframework.config.ConfigReader;
import com.uberframework.pages.LoginPage;
import com.uberframework.pages.PaymentPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PaymentTest extends BaseTest {

    private LoginPage loginPage;
    private PaymentPage paymentPage;

    @BeforeMethod
    public void setUpPage() {
        loginPage = new LoginPage(driver);
        paymentPage = new PaymentPage(driver);

        // Login before each test
        loginPage.enterCredentialAndContinue(
        	    ConfigReader.getEmail());

        // Navigate to payment section
        paymentPage.clickPaymentMenu();
    }

    @Test(priority = 1,
          description = "Verify add valid card")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Payment")
    @Description("Add a valid credit card")
    public void testAddValidCard() {
        paymentPage.addCard(
            "4111111111111111",
            "12/26",
            "123",
            "Test User");
        Assert.assertTrue(
            paymentPage.isPaymentSuccessful(),
            "Card was not added successfully!");
    }

    @Test(priority = 2,
          description = "Verify add invalid card")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Payment")
    @Description("Add an invalid credit card")
    public void testAddInvalidCard() {
        paymentPage.addCard(
            "1234567890",
            "13/99",
            "000",
            "Test User");
        Assert.assertFalse(
            paymentPage.isPaymentSuccessful(),
            "Invalid card should not be added!");
    }

    @Test(priority = 3,
          description = "Verify add UPI")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Payment")
    @Description("Add a valid UPI ID")
    public void testAddValidUPI() {
        paymentPage.addUPI("testuser@upi");
        Assert.assertTrue(
            paymentPage.isPaymentSuccessful(),
            "UPI was not added successfully!");
    }

    @Test(priority = 4,
          description = "Verify invalid UPI")
    @Severity(SeverityLevel.NORMAL)
    @Story("Payment")
    @Description("Add an invalid UPI ID")
    public void testAddInvalidUPI() {
        paymentPage.addUPI("invaldupi");
        Assert.assertFalse(
            paymentPage.isPaymentSuccessful(),
            "Invalid UPI should not be added!");
    }

    @Test(priority = 5,
          description = "Verify valid promo code")
    @Severity(SeverityLevel.NORMAL)
    @Story("Payment")
    @Description("Apply a valid promo code")
    public void testValidPromoCode() {
        paymentPage.applyPromoCode("UBER50");
        Assert.assertTrue(
            paymentPage.isPromoApplied(),
            "Valid promo code should be applied!");
    }

    @Test(priority = 6,
          description = "Verify invalid promo code")
    @Severity(SeverityLevel.NORMAL)
    @Story("Payment")
    @Description("Apply an invalid promo code")
    public void testInvalidPromoCode() {
        paymentPage.applyPromoCode("INVALID123");
        Assert.assertFalse(
            paymentPage.isPromoApplied(),
            "Invalid promo code should not be applied!");
    }

    @Test(priority = 7,
          description = "Verify wallet balance displayed")
    @Severity(SeverityLevel.NORMAL)
    @Story("Payment")
    @Description("Verify wallet balance is displayed")
    public void testWalletBalanceDisplayed() {
        String balance = paymentPage.getWalletBalance();
        Assert.assertNotNull(balance,
            "Wallet balance not displayed!");
    }

    @Test(priority = 8,
          description = "Verify fare breakdown")
    @Severity(SeverityLevel.NORMAL)
    @Story("Payment")
    @Description("Verify fare breakdown shows all components")
    public void testFareBreakdown() {
        String baseFare = paymentPage.getBaseFare();
        String taxes = paymentPage.getTaxes();
        String totalFare = paymentPage.getTotalFare();

        Assert.assertNotNull(baseFare,
            "Base fare not displayed!");
        Assert.assertNotNull(taxes,
            "Taxes not displayed!");
        Assert.assertNotNull(totalFare,
            "Total fare not displayed!");
    }

    @Test(priority = 9,
          dataProvider = "promoData",
          description = "Data driven promo code test")
    @Severity(SeverityLevel.NORMAL)
    @Story("Payment")
    @Description("Data driven test for promo codes")
    public void testDataDrivenPromoCode(
            String promoCode, String expectedResult) {
        paymentPage.applyPromoCode(promoCode);
        if (expectedResult.equals("success")) {
            Assert.assertTrue(
                paymentPage.isPromoApplied(),
                "Promo should be applied: " + promoCode);
        } else {
            Assert.assertFalse(
                paymentPage.isPromoApplied(),
                "Promo should fail: " + promoCode);
        }
    }

    @DataProvider(name = "promoData")
    public Object[][] getPromoData() {
        return new Object[][] {
            {"UBER50",    "success"},
            {"FIRST100",  "success"},
            {"INVALID",   "failure"},
            {"EXPIRED99", "failure"},
            {"",          "failure"}
        };
    }
}