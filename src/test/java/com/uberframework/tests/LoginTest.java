package com.uberframework.tests;

import com.uberframework.base.BaseTest;
import com.uberframework.config.ConfigReader;
import com.uberframework.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUpPage() {
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1,
          description = "Verify valid login")
    @Severity(SeverityLevel.BLOCKER)
    @Story("User Login")
    @Description("Test valid login with correct credentials")
    public void testValidLogin() {
        loginPage.login(
            ConfigReader.getEmail(),
            ConfigReader.getPassword());
        Assert.assertTrue(
            loginPage.isLoginSuccessful(),
            "Login failed with valid credentials!");
    }

    @Test(priority = 2,
          description = "Verify invalid login")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User Login")
    @Description("Test invalid login with wrong credentials")
    public void testInvalidLogin() {
        loginPage.login(
            "wrong@gmail.com",
            "WrongPass@123");
        Assert.assertFalse(
            loginPage.isLoginSuccessful(),
            "Login should have failed!");
    }

    @Test(priority = 3,
          description = "Verify empty email login")
    @Severity(SeverityLevel.NORMAL)
    @Story("User Login")
    @Description("Test login with empty email field")
    public void testEmptyEmailLogin() {
        loginPage.login("", "Test@1234");
        Assert.assertFalse(
            loginPage.isLoginSuccessful(),
            "Login should fail with empty email!");
    }

    @Test(priority = 4,
          description = "Verify empty password login")
    @Severity(SeverityLevel.NORMAL)
    @Story("User Login")
    @Description("Test login with empty password field")
    public void testEmptyPasswordLogin() {
        loginPage.login(
            ConfigReader.getEmail(), "");
        Assert.assertFalse(
            loginPage.isLoginSuccessful(),
            "Login should fail with empty password!");
    }

    @Test(priority = 5,
          description = "Verify invalid email format")
    @Severity(SeverityLevel.MINOR)
    @Story("User Login")
    @Description("Test login with invalid email format")
    public void testInvalidEmailFormat() {
        loginPage.login(
            "invalidemail", "Test@1234");
        Assert.assertFalse(
            loginPage.isLoginSuccessful(),
            "Login should fail with invalid email format!");
    }

    @Test(priority = 6,
          dataProvider = "loginData",
          description = "Data driven login test")
    @Severity(SeverityLevel.CRITICAL)
    @Story("User Login")
    @Description("Data driven login with multiple credentials")
    public void testDataDrivenLogin(String email,
                                     String password,
                                     String expectedResult) {
        loginPage.login(email, password);
        if (expectedResult.equals("success")) {
            Assert.assertTrue(
                loginPage.isLoginSuccessful(),
                "Login should succeed for: " + email);
        } else {
            Assert.assertFalse(
                loginPage.isLoginSuccessful(),
                "Login should fail for: " + email);
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            {"test@gmail.com", "Test@1234", "success"},
            {"wrong@gmail.com", "wrong", "failure"},
            {"", "Test@1234", "failure"},
            {"test@gmail.com", "", "failure"},
            {"invalid-email", "Test@1234", "failure"}
        };
    }
}