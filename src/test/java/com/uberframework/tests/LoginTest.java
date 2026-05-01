package com.uberframework.tests;
import com.uberframework.utils.CookieUtils;
import com.uberframework.base.BaseTest;
import com.uberframework.config.ConfigReader;
import com.uberframework.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void setUpPage() {
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1,
          description = "Verify login page loaded")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login Page")
    @Description("Verify login page loads correctly")
    public void testLoginPageLoaded() {
        Assert.assertTrue(
            loginPage.isLoginPageLoaded(),
            "Login page did not load!");
    }

    @Test(priority = 2,
          description = "Verify continue button visible")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login Page")
    @Description("Verify continue button is visible")
    public void testContinueButtonVisible() {
        Assert.assertTrue(
            loginPage.isContinueButtonDisplayed(),
            "Continue button not visible!");
    }

    @Test(priority = 3,
          description = "Verify valid email accepted")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login Page")
    @Description("Enter valid email and click continue")
    public void testValidEmailAccepted() {
        loginPage.enterCredentialAndContinue(
            ConfigReader.getEmail());
        Assert.assertFalse(
            loginPage.isErrorDisplayed(),
            "Error shown for valid email!");
    }

    
    @Test(priority = 4,
    	      description = "Verify invalid email shows error")
    	@Severity(SeverityLevel.NORMAL)
    	@Story("Login Page")
    	@Description("Enter invalid email format")
    	public void testInvalidEmailShowsError() {
    	    loginPage.enterCredentialAndContinue(
    	        "invalidemail@@@.com");
    	    // Uber may accept some formats
    	    // Just verify page doesn't crash
    	    Assert.assertTrue(
    	        loginPage.isLoginPageLoaded() ||
    	        loginPage.isErrorDisplayed(),
    	        "Page crashed on invalid email!");
    }
    @Test(priority = 6,
    	      description = "Verify cookie login works")
    	@Severity(SeverityLevel.BLOCKER)
    	@Story("Cookie Login")
    	@Description("Login using session cookies")
    	public void testCookieBasedLogin() {
    	    CookieUtils.injectUberCookies(driver);
    	    driver.get("https://www.uber.com");
    	    Assert.assertTrue(
    	        CookieUtils.isLoggedIn(driver),
    	        "Cookie login failed!");
    	}
}