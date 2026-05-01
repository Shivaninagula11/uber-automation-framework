package com.uberframework.base;
import java.time.Duration;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.uberframework.config.ConfigReader;
import com.uberframework.utils.DriverFactory;
import com.uberframework.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = 
        new ThreadLocal<>();

    // Runs once before entire suite
    @BeforeSuite
    public void setUpReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter(
            ConfigReader.getReportPath());
        spark.config().setDocumentTitle(
            "Uber Automation Report");
        spark.config().setReportName(
            "Uber Test Results");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Project", 
            "Uber Automation Framework");
        extent.setSystemInfo("Tester", "QA Engineer");
        extent.setSystemInfo("Environment", "Production");
    }

    // Runs before each test method
    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.initDriver(browser);
        driver.get(ConfigReader.getUrl());
        // Wait for page to load
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        }

    // Runs after each test method
    @AfterMethod
    public void tearDown(ITestResult result) {
        ExtentTest extentTest = test.get();

        if (result.getStatus() == ITestResult.FAILURE) {
            // Capture screenshot on failure
            String screenshotPath = ScreenshotUtils
                .captureScreenshot(driver, 
                    result.getName());
            if (extentTest != null) {
                extentTest.log(Status.FAIL, 
                    "Test Failed: " 
                    + result.getThrowable());
                extentTest.addScreenCaptureFromPath(
                    screenshotPath);
            }
        } else if (result.getStatus() 
                == ITestResult.SUCCESS) {
            if (extentTest != null) {
                extentTest.log(Status.PASS, 
                    "Test Passed!");
            }
        } else {
            if (extentTest != null) {
                extentTest.log(Status.SKIP, 
                    "Test Skipped!");
            }
        }

        DriverFactory.quitDriver();
    }

    // Runs once after entire suite
    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}