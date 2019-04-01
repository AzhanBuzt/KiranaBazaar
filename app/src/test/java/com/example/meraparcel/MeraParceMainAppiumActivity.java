package com.example.meraparcel;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import excelSupport.DatafromExcel;
import utility.*;

import static com.example.meraparcel.Locators.*;
import static io.appium.java_client.android.Connection.AIRPLANE;
import static utility.capability.*;


public class MeraParceMainAppiumActivity {

    static AndroidDriver driver;

    WebDriverWait wait;
    ExtentReports report;
    ExtentTest test;
    public DatafromExcel objgetdata;
    public String TestCaseName, TestCaseType, TestCaseDescription;
    private Properties properties;
    BufferedReader reader;
    private final String propertyFilePath = "../app/src/test/java/utility/Data.properties";
    int iteration = 1;

    //private ZipCodeValidator zipCodeValidator;

    @BeforeTest(alwaysRun = true)
    public void setUp() throws Exception {
        //Loading Property Details
        reader = new BufferedReader(new FileReader(propertyFilePath));
        properties = new Properties();
        properties.load(reader);

        //setting logs to project
        PropertyConfigurator.configure(properties.getProperty("logFilePath"));
        // Created object of DesiredCapabilities class.
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // Set android deviceName desired capability. Set your device name.
        capabilities.setCapability("deviceName", deviceName);

        // Set BROWSER_NAME desired capability. It's Android in our case here.
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");

        // Set android VERSION desired capability. Set your mobile device's OS version.
        capabilities.setCapability(CapabilityType.VERSION, deviceVersion);
        // Set android platformName desired capability. It's Android in our case here.
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appActivity", appActivity);
        capabilities.setCapability("unicodeKeyboard", t);
        capabilities.setCapability("resetKeyboard", t);
        capabilities.setCapability("automationName", uiAutomator2);
        //Command time out if connected device respond slow
        capabilities.setCapability("newCommandTimeout", "45000");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, uiAutomator2);
        capabilities.setCapability("autoGrantPermissions", t);
        capabilities.setCapability("autoAcceptAlerts", t);
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        objgetdata = new DatafromExcel(properties.getProperty("excelSheetPath"));
        report = new ExtentReports(properties.getProperty("extentReportPath"), true);
        report.addSystemInfo("Extent Report Version", "2.05");
        report.addSystemInfo("Environment", "Test");
        report.config()
                .documentTitle("Kirana Bazaar Test Report")
                .reportName("Appium Report-")
                .reportHeadline("Test automation report of Kirana Bazaar by Aaqib")
                .insertCustomStyles(".test { border:2px solid #444; }");

        Thread.sleep(4000);
    }

    @Test(priority = 1, groups = {"ui"})
    public void LoginElementVisibilityCheck() throws Exception {
        TestCaseName = objgetdata.GetData(0, 1, 1);
        TestCaseType = objgetdata.GetData(0, 1, 2);
        TestCaseDescription = objgetdata.GetData(0, 1, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "LoginElementVisibilityCheck");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully");
        if (driver.getConnection() != AIRPLANE) {
            wait = new WebDriverWait(driver, 20);
            WebElement SignUpButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(signUpbtn)));
            test.log(LogStatus.INFO, "Step 2 :Sign Up button is visible on Login Activity");
            WebElement LoginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(loginbtn)));
            test.log(LogStatus.INFO, "Step 3 :Login button is visible on Login Activity");
            WebElement UserName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(editTextEmail)));
            test.log(LogStatus.INFO, "Step 4 :User Name editext is visible on Login Activity");
            WebElement PassWord = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(editTextPassword)));
            test.log(LogStatus.INFO, "Step 5 :Password editext is visible on Login Activity");
            WebElement ForgotLinkbtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            test.log(LogStatus.INFO, "Step 6 :ForgotLink button is visible on Login Activity");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            test.log(LogStatus.PASS, "All the UI Element has been verified as available.");
        } else {
            test.log(LogStatus.WARNING, "Test Case has been Skipped because Internet is unavailable - " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }

    }

    @Test(priority = 2, groups = {"functional"})
    public void Validate_LoginEmpty() throws Exception {
        TestCaseName = objgetdata.GetData(0, 2, 1);
        TestCaseType = objgetdata.GetData(0, 2, 2);
        TestCaseDescription = objgetdata.GetData(0, 2, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_LoginEmpty");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {
            //clearing email & password to make it blank
            driver.findElement(By.xpath(editTextDynamic)).clear();
            test.log(LogStatus.INFO, "Step 2 : Password Textbox is empty now.");
            driver.findElement(By.xpath(editPassDynamic)).clear();
            test.log(LogStatus.INFO, "Step 3 : Email Textbox is empty now.");
            //clicking the login btn
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(loginbtn)).click();
            test.log(LogStatus.INFO, "Step 4 : Button Login has been clicked successfully.");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            WebElement toastView = driver.findElement(By.xpath(toastMessage));
            test.log(LogStatus.INFO, "Step 5 : Toast Message text has been captured.");
            String es = toastView.getText();
            System.out.println(es);
            Assert.assertTrue(es.contains(properties.getProperty("verifyEmpty")));
            test.log(LogStatus.PASS, "Step 6 : Toast Text has been verified as " + es);

        } else {
            test.log(LogStatus.WARNING, "Test Case has been Skipped because Internet is unavailable - " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");

        }
    }

    @Test(priority = 3, groups = {"functional"})
    public void Validate_ValidEmailEmptyPassword() throws Exception {

        TestCaseName = objgetdata.GetData(0, 3, 1);
        TestCaseType = objgetdata.GetData(0, 3, 2);
        TestCaseDescription = objgetdata.GetData(0, 3, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_ValidEmailEmptyPassword");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {

            //clearing email & password to make it blank
            driver.findElement(By.xpath(editPassDynamic)).clear();
            test.log(LogStatus.INFO, "Step 2 : Password Textbox is empty now.");
            driver.findElement(By.xpath(editTextDynamic)).clear();
            test.log(LogStatus.INFO, "Step 3 : Email Textbox is empty now.");
            driver.findElement(By.xpath(editTextEmail)).sendKeys(properties.getProperty("validEmail"));
            test.log(LogStatus.INFO, "Step 4 : Valid Email has been inserted.");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            //clicking the login btn
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(loginbtn)).click();
            test.log(LogStatus.INFO, "Step 4 : Button Login has been clicked successfully.");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            WebElement toastView = driver.findElement(By.xpath(toastMessage));
            test.log(LogStatus.INFO, "Step 5 : Toast Message text has been captured.");
            String es = toastView.getText();
            System.out.println(es);
            Assert.assertTrue(es.contains(properties.getProperty("verifyWithEmail")));
            test.log(LogStatus.PASS, "Step 6 : Toast Text has been verified as " + es);
        } else {
            test.log(LogStatus.WARNING, "Test Case has been Skipped because Internet is unavailable - " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }
    }

    @Test(priority = 4, groups = {"functional"})
    public void Validate_EmailFormatWhileLogin() throws Exception {

        TestCaseName = objgetdata.GetData(0, 4, 1);
        TestCaseType = objgetdata.GetData(0, 4, 2);
        TestCaseDescription = objgetdata.GetData(0, 4, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_EmailFormatWhileLogin");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {
            //clearing email & password to make it blank
            driver.findElement(By.xpath(editPassDynamic)).clear();
            test.log(LogStatus.INFO, "Step 2 : Password Textbox is empty now.");
            driver.findElement(By.xpath(editTextPassword)).sendKeys(properties.getProperty("validPassword"));
            test.log(LogStatus.INFO, "Step 3 : Any password has been filled.");
            driver.findElement(By.xpath(editTextDynamic)).clear();
            test.log(LogStatus.INFO, "Step 4 : Email Textbox is empty now.");
            for (int i = 5; i <= 6; i++) {

                String generatedString = RandomStringUtils.randomAlphabetic(10);
                generatedString = generatedString.concat("#@gm.co.in");
                System.out.println(generatedString);
                driver.findElement(By.xpath(editTextDynamic)).sendKeys(generatedString);
                test.log(LogStatus.INFO, "Step " + i + " : " + iteration + " iteration of invalid Email has been inserted.");
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
                driver.findElement(By.xpath(loginbtn)).click();
                //clicking the login btn
                test.log(LogStatus.INFO, "Step " + i + " : " + iteration + " iteration of login button has been clicked.");
                Thread.sleep(Integer.parseInt(properties.getProperty("midWait")));
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                WebElement toastView = driver.findElement(By.xpath(toastMessage));
                String es = toastView.getText();
                System.out.println(es);
                Assert.assertFalse(es.contains(properties.getProperty("verifyValidEmailFormat")));
                test.log(LogStatus.INFO, "Step " + i + " : " + iteration + " iternation of Toast Text has been verified as " + es);
                driver.findElement(By.xpath(editTextDynamic)).clear();

                iteration = iteration + 1;
            }
            test.log(LogStatus.PASS, "Step 7 : Email Validation is not implemented at Kirana Bazaar login.");
            driver.findElement(By.xpath(editPassDynamic)).clear();
        } else {
            test.log(LogStatus.WARNING, "Test Case has been Skipped because Internet is unavailable - " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }
    }

    @Test(priority = 5, groups = {"functional"})
    public void Validate_InternetConnectivity() {

        TestCaseName = objgetdata.GetData(0, 5, 1);
        TestCaseType = objgetdata.GetData(0, 5, 2);
        TestCaseDescription = objgetdata.GetData(0, 5, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_InternetConnectivity");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {

            test.log(LogStatus.PASS, "Step 2 : Current Connectivity is of " + driver.getConnection());
        } else {
            test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }

    }

    @Test(priority = 6, groups = {"navigation"})
    public void Validate_Navigation_To_ForgotPassWordActivity() throws Exception {

        TestCaseName = objgetdata.GetData(0, 6, 1);
        TestCaseType = objgetdata.GetData(0, 6, 2);
        TestCaseDescription = objgetdata.GetData(0, 6, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_Navigation_To_ForgotPassWordActivity");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        String currentActivity = driver.currentActivity();
        Assert.assertTrue(currentActivity.equals(properties.getProperty("loginActivity")));
        test.log(LogStatus.INFO, "Step 2 :Current Activity is verified as Login");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        WebElement ForgotLinkbtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
        test.log(LogStatus.INFO, "Step 3 :ForgotLink button is visible on Login Activity");
        Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
        ForgotLinkbtn.click();
        test.log(LogStatus.INFO, "Step 4 :ForgotLink button is clicked Successfully");
        GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ForgotActivity)));
        test.log(LogStatus.INFO, "Step 5 :Forgot Activity is visible on App screen.");
        Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
        driver.findElement(By.xpath(forgotBackBtn)).click();
        test.log(LogStatus.INFO, "Step 6 :Back button is clicked Successfully.");
        Assert.assertTrue(currentActivity.equals(properties.getProperty("loginActivity")));
        test.log(LogStatus.PASS, "Step 7 :User is back at Login Activity.");
    }


    @Test(priority = 7, groups = {"functional"})
    public void Validate_emptyEmailInForgotPassword() throws Exception {


        TestCaseName = objgetdata.GetData(0, 7, 1);
        TestCaseType = objgetdata.GetData(0, 7, 2);
        TestCaseDescription = objgetdata.GetData(0, 7, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_emptyEmailInForgotPassword");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {

            test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of " + driver.getConnection());

            String currentActivity = driver.currentActivity();
            Assert.assertTrue(currentActivity.equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 3 :Current Activity is verified as Login");
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 20);
            WebElement ForgotLinkbtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            test.log(LogStatus.INFO, "Step 4 :ForgotLink button is visible on Login Activity");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            ForgotLinkbtn.click();
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.findElement(By.xpath(editTextDynamic)).clear();
            test.log(LogStatus.INFO, "Step 5 :Forgot password has been cleared successfully.");
            driver.findElement(By.xpath(forgotBtn)).click();
            test.log(LogStatus.INFO, "Step 6 :Forgot button has been clicked successfully.");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            WebElement toastView = driver.findElement(By.xpath(toastMessage));
            test.log(LogStatus.INFO, "Step 7 : Toast Message text has been captured.");
            String es = toastView.getText();
            System.out.println(es);
            Assert.assertTrue(es.contains(properties.getProperty("verifyEmpty")));
            test.log(LogStatus.PASS, "Step 8 : Toast Text has been verified as " + es);
            driver.findElement(By.xpath(forgotBackBtn)).click();
            test.log(LogStatus.INFO, "Step 9 :Back button is clicked Successfully.");
            Assert.assertTrue(currentActivity.equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 10 :User is back at Login Activity.");
        } else {
            test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }

    }

    @Test(priority = 8, groups = {"functional"})
    public void Validate_EmailFormat_WhileForgotPassword() throws Exception {

        TestCaseName = objgetdata.GetData(0, 8, 1);
        TestCaseType = objgetdata.GetData(0, 8, 2);
        TestCaseDescription = objgetdata.GetData(0, 8, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_EmailFormat_WhileForgotPassword");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {
            String currentActivity = driver.currentActivity();
            Assert.assertTrue(currentActivity.equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 2 :Current Activity is verified as Login");
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 20);
            WebElement ForgotLinkbtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            test.log(LogStatus.INFO, "Step 3 :ForgotLink button is visible on Login Activity");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            ForgotLinkbtn.click();
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            String generatedString = RandomStringUtils.randomAlphabetic(10);
            generatedString = generatedString.concat("#@gm.co.in");
            System.out.println(generatedString);
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(editTextDynamic)).clear();
            test.log(LogStatus.INFO, "Step 4 :Forgot password has been cleared successfully.");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.findElement(By.xpath(editTextDynamic)).sendKeys(generatedString.trim());
            test.log(LogStatus.INFO, "Step 5 :Invalid Email has been set successfully.");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.findElement(By.xpath(forgotBtn)).click();
            test.log(LogStatus.INFO, "Step 6 :Forgot button has been clicked successfully.");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            WebElement toastView = driver.findElement(By.xpath(toastMessage));
            test.log(LogStatus.INFO, "Step 7 : Toast Message text has been captured.");
            String es = toastView.getText();
            System.out.println(es);
            Assert.assertFalse(es.contains(properties.getProperty("verifyEmpty")));
            test.log(LogStatus.PASS, "Step 8 : Toast Text has been verified as " + es);
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.findElement(By.xpath(forgotBackBtn)).click();
            test.log(LogStatus.INFO, "Step 9 :Back button is clicked Successfully.");
            Assert.assertTrue(currentActivity.equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 10 :User is back at Login Activity.");
        } else {
            test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }

    }

    @Test(priority = 9, groups = {"functional1"})
    public void Validate_EmailSentToUser_WhileForgotPassword() throws Exception {

        TestCaseName = objgetdata.GetData(0, 9, 1);
        TestCaseType = objgetdata.GetData(0, 9, 2);
        TestCaseDescription = objgetdata.GetData(0, 9, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_EmailSentToUser_WhileForgotPassword");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {
            String currentActivity = driver.currentActivity();
            Assert.assertTrue(currentActivity.equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 2 :Current Activity is verified as Login");
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 10);
            WebElement ForgotLinkbtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            test.log(LogStatus.INFO, "Step 3 :ForgotLink button is visible on Login Activity");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            ForgotLinkbtn.click();
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(editTextDynamic)).clear();
            test.log(LogStatus.INFO, "Step 4 :Forgot password has been cleared successfully.");
            driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("validEmail").trim());
            test.log(LogStatus.INFO, "Step 5 :Valid Forgot Email has been set successfully.");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.findElement(By.xpath(forgotBtn)).click();
            test.log(LogStatus.INFO, "Step 6 :Forgot button has been clicked successfully.");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            WebElement toastView = driver.findElement(By.xpath(toastMessage));
            test.log(LogStatus.INFO, "Step 7 : Toast Message text has been captured.");
            String es = toastView.getText();
            System.out.println(es);
            Assert.assertTrue(es.equals(properties.getProperty("forgotPassword")));
            test.log(LogStatus.PASS, "Step 8 : Toast Text has been verified as " + es);
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            Thread.sleep(Integer.parseInt(properties.getProperty("midWait")));
            driver.findElement(By.xpath(forgotBackBtn)).click();
            test.log(LogStatus.INFO, "Step 9 :Back button is clicked Successfully.");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            Assert.assertTrue(currentActivity.equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 10 :User is back at Login Activity.");
        } else {
            test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }

    }

    @Test(priority = 10, groups = {"login"})
    public void Validate_SuccessfulLogin() throws Exception {

        TestCaseName = objgetdata.GetData(0, 10, 1);
        TestCaseType = objgetdata.GetData(0, 10, 2);
        TestCaseDescription = objgetdata.GetData(0, 10, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_SuccessfulLogin");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {
            Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 2 :Current Activity is verified as Login");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.findElement(By.xpath(editTextDynamic)).clear();
            driver.findElement(By.xpath(editPassDynamic)).clear();
            test.log(LogStatus.INFO, "Step 3 :UserName and Password cleared Successfully");
            driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("validEmail").trim());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(editPassDynamic)).sendKeys(properties.getProperty("validPassword").trim());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(loginbtn)).click();
            test.log(LogStatus.INFO, "Step 4 :Login button has been clicked Successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardActivity)));
            Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("dashboardActivity")));
            test.log(LogStatus.INFO, "Step 5 :Current Activity is verified as " + driver.currentActivity());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(dashboardMenu)).click();
            test.log(LogStatus.INFO, "Step 6 :Dashboard icon has been clicked successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            int x1 = driver.findElement(By.xpath(shareAppMenu)).getLocation().x;
            int y1 = driver.findElement(By.xpath(shareAppMenu)).getLocation().y;
            int x = driver.findElement(By.xpath(homeMenu)).getLocation().x;
            int y = driver.findElement(By.xpath(homeMenu)).getLocation().y;
            test.log(LogStatus.INFO, "Step 7 :Dynamic co-ordinates location has been fetched successfully");
            TouchAction actions = new TouchAction(driver);
            actions.press(x1, y1).waitAction(Integer.parseInt(properties.getProperty("minWait"))).moveTo(x, y).release().perform();
            test.log(LogStatus.INFO, "Step 8 :Menu item has been scrolled successfully");
            driver.findElement(By.xpath(logoutMenu)).click();
            test.log(LogStatus.PASS, "Step 9 :Application has been logged out successfully");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));

        } else {
            test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }

    }

    @Test(priority = 11, groups = {"login"})
    public void Navigate_AllMenuItems() throws Exception {

        TestCaseName = objgetdata.GetData(0, 11, 1);
        TestCaseType = objgetdata.GetData(0, 11, 2);
        TestCaseDescription = objgetdata.GetData(0, 11, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Navigate_AllMenuItems");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 2 :Current Activity is verified as Login");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.findElement(By.xpath(editTextDynamic)).clear();
            driver.findElement(By.xpath(editPassDynamic)).clear();
            test.log(LogStatus.INFO, "Step 3 :UserName and Password cleared Successfully");
            driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("validEmail").trim());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(editPassDynamic)).sendKeys(properties.getProperty("validPassword").trim());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(loginbtn)).click();
            test.log(LogStatus.INFO, "Step 4 :Login button has been clicked Successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardActivity)));
            Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("dashboardActivity")));
            test.log(LogStatus.INFO, "Step 5 :Current Activity is verified as " + driver.currentActivity());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(dashboardMenu)).click();
            test.log(LogStatus.INFO, "Step 6 :Dashboard icon has been clicked successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.findElement(By.xpath(homeMenu)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            test.log(LogStatus.INFO, "Step 7 :Home Menu has been clicked successfully");
            driver.findElement(By.xpath(dashboardMenu)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(orderMenu)).click();
            test.log(LogStatus.INFO, "Step 8 :My Order Menu has been clicked successfully");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.findElement(By.xpath(dashboardMenu)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(cartMenu)).click();
            test.log(LogStatus.INFO, "Step 9 :Cart Menu has been clicked successfully");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.findElement(By.xpath(dashboardMenu)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minMidWait")));
            driver.findElement(By.xpath(contactUsMenu)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minMidWait")));
            test.log(LogStatus.INFO, "Step 10 :Contact Us Menu has been clicked successfully");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.pressKeyCode(AndroidKeyCode.BACK);
            driver.findElement(By.xpath(dashboardMenu)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(feedBackMenu)).click();
            test.log(LogStatus.INFO, "Step 11 :FeedBack Menu has been clicked successfully");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.findElement(By.xpath(dashboardMenu)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(editProfileMenu)).click();
            test.log(LogStatus.INFO, "Step 12 :Edit Profile Menu has been clicked successfully");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.findElement(By.xpath(dashboardMenu)).click();
            int x1 = driver.findElement(By.xpath(shareAppMenu)).getLocation().x;
            int y1 = driver.findElement(By.xpath(shareAppMenu)).getLocation().y;
            int x = driver.findElement(By.xpath(homeMenu)).getLocation().x;
            int y = driver.findElement(By.xpath(homeMenu)).getLocation().y;
            TouchAction actions1 = new TouchAction(driver);
            actions1.press(x1, y1).waitAction(2000).moveTo(x, y).release().perform();
            driver.findElement(By.xpath(shareAppMenu)).click();
            test.log(LogStatus.INFO, "Step 13 :Share App Menu has been clicked successfully");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            Thread.sleep(Integer.parseInt(properties.getProperty("minMidWait")));
            TouchAction actions2 = new TouchAction(driver);
            actions2.tap(150, 72).waitAction(Integer.parseInt(properties.getProperty("minWait"))).perform().release();
            driver.findElement(By.xpath(dashboardMenu)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(notificationMenu)).click();
            test.log(LogStatus.INFO, "Step 14 :Notification Menu has been clicked successfully");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(notificationBack)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(logoutMenu)).click();
            test.log(LogStatus.PASS, "Step 15 :Logout Menu has been clicked successfully");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
        } else {
            test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }


    }

    @Test(priority = 12, groups = {"share"})
    public void Application_Share_Through_WhatsApp() throws Exception {

        TestCaseName = objgetdata.GetData(0, 12, 1);
        TestCaseType = objgetdata.GetData(0, 12, 2);
        TestCaseDescription = objgetdata.GetData(0, 12, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Application_Share_Through_WhatsApp");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 2 :Current Activity is verified as Login");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.findElement(By.xpath(editTextDynamic)).clear();
            driver.findElement(By.xpath(editPassDynamic)).clear();
            test.log(LogStatus.INFO, "Step 3 :UserName and Password cleared Successfully");
            driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("validEmail").trim());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(editPassDynamic)).sendKeys(properties.getProperty("validPassword").trim());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(loginbtn)).click();
            test.log(LogStatus.INFO, "Step 4 :Login button has been clicked Successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardActivity)));
            Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("dashboardActivity")));
            test.log(LogStatus.INFO, "Step 5 :Current Activity is verified as " + driver.currentActivity());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(dashboardMenu)).click();
            test.log(LogStatus.INFO, "Step 6 :Dashboard icon has been clicked successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(shareAppMenu)).click();
            test.log(LogStatus.INFO, "Step 7 :Share App Menu has been clicked successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("midMaxWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(whatsApp)));
            test.log(LogStatus.INFO, "Step 8 :Social Media Element WhatsApp has been found successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(whatsApp)).click();
            test.log(LogStatus.INFO, "Step 9 :WhatsApp has been clicked successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minMidWait")));
            TouchAction actions = new TouchAction(driver);
            actions.tap(50, 567).release().perform();
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            test.log(LogStatus.INFO, "Step 10 :Most frequent contacted on whatsApp has been clicked successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("midMaxWait")));
            driver.findElement(By.xpath(whatsAppImageButton)).click();
            test.log(LogStatus.INFO, "Step 10 :WhatsApp Send has been clicked successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            System.out.println(driver.findElement(By.xpath(editTextDynamic)).getText());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            driver.findElement(By.xpath(whatsAppImageButton)).click();
            test.log(LogStatus.PASS, "Step 11 :Share Link has been shared successfully");
            driver.pressKeyCode(AndroidKeyCode.BACK);
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.pressKeyCode(AndroidKeyCode.BACK);
            test.log(LogStatus.INFO, "Step 12 :Focus has been moved to Kirana Bazaar from WhatsApp successfully");
            driver.findElement(By.xpath(dashboardMenu)).click();
            int x1 = driver.findElement(By.xpath(shareAppMenu)).getLocation().x;
            int y1 = driver.findElement(By.xpath(shareAppMenu)).getLocation().y;
            int x = driver.findElement(By.xpath(homeMenu)).getLocation().x;
            int y = driver.findElement(By.xpath(homeMenu)).getLocation().y;
            TouchAction actions2 = new TouchAction(driver);
            actions2.press(x1, y1).waitAction(Integer.parseInt(properties.getProperty("minWait"))).moveTo(x, y).release().perform();
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            test.log(LogStatus.INFO, "Step 13 :Menu item has been scrolled successfully");
            driver.findElement(By.xpath(logoutMenu)).click();
            test.log(LogStatus.INFO, "Step 14 :Application has been logged out successfully");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
        } else {
            test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }

    }

    @Test(priority = 13, groups = {"functional"})
    public void Validate_validPinCodeAtRegistrationPage() throws Exception {

        TestCaseName = objgetdata.GetData(0, 13, 1);
        TestCaseType = objgetdata.GetData(0, 13, 2);
        TestCaseDescription = objgetdata.GetData(0, 13, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_validPinCodeAtRegistrationPage");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {
            wait = new WebDriverWait(driver, 20);
            WebElement SignUpButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(signUpbtn)));
            test.log(LogStatus.INFO, "Step 2 :Sign Up button is visible on Login Activity");
            SignUpButton.click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            test.log(LogStatus.INFO, "Step 3 :Sign Up button has been clicked successfully");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            ZipCodeValidator z = new ZipCodeValidator();
            WebElement pinCode = driver.findElement(By.xpath(editInPinCode));
            for (int i = 3; i <= 6; i++) {

                if (i == 3) {
                    //it will validate the invalid scenario
                    pinCode.sendKeys(properties.getProperty("inValidPinCode"));
                    Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
                    test.log(LogStatus.INFO, "Step " + i + " (A): Invalid PinCode has been entered as " + properties.getProperty("inValidPinCode"));
                    boolean b = z.validate(properties.getProperty("inValidPinCode"));
                    Assert.assertFalse(b);
                    GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                    test.log(LogStatus.INFO, "Step " + i + " (B): PinCode has been verified as invalid through Assertion");
                }

                if (i == 4) {
                    //it will validate the valid scenario
                    pinCode.sendKeys(properties.getProperty("validPinCode"));
                    Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
                    test.log(LogStatus.INFO, "Step " + i + " (A): Valid PinCode has been entered as " + properties.getProperty("validPinCode"));
                    boolean b = z.validate(properties.getProperty("validPinCode"));
                    Assert.assertTrue(b);
                    GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                    test.log(LogStatus.INFO, "Step " + i + " (B): PinCode has been verified as valid through Assertion");
                }
                if (i == 5) {
                    //it will validate the max length of char is allowed for zipCode field
                    String length = "";
                    pinCode.clear();
                    test.log(LogStatus.INFO, "Step " + i + " (A): Char sequence will be inserted automatically and verify max length");
                    for (int input = 0; ; input++) {
                        length = length.concat(String.valueOf(input));
                        pinCode.sendKeys(length);
                        if (pinCode.getText().length() == input) {
                            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                            test.log(LogStatus.INFO, "Step " + i + " (B): Only " + pinCode.getText().length() + " char entry is allowed in pincode");
                            break;
                            //to break the loop
                        }
                    }
                }
                if (i == 6) {
                    //it will validate the pinCode and address for DDU(Deen-Dayal-Upadhyay Junction) from PostalAPi
                    String JsonStringData = ZipCodeValidator.sendRequest(properties.getProperty("zipCodeApi"));
                    if (!JsonStringData.isEmpty()) {
                        test.log(LogStatus.INFO, "Step " + i + " (A): JsonString received from APi " + JsonStringData);
                        String local = ZipCodeValidator.parseFromJSONResponse(JsonStringData);
                        test.log(LogStatus.INFO, "Step " + i + " (B): Names has been parsed from JsonString received from APi " + local);
                        String[] arrayOfNames = local.split("\\s*,\\s*");
                        int ArrayItems = arrayOfNames.length;
                        for (int lc = 0; lc < ArrayItems; lc++) {
                            driver.findElement(By.xpath(editInAddress)).clear();
                            driver.findElement(By.xpath(editInAddress)).sendKeys(arrayOfNames[lc]);
                            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
                        }

                    } else {

                        test.log(LogStatus.WARNING, "APi has no response data when pinged.");
                        test.log(LogStatus.SKIP, "Test Case Skipped");
                    }

                }
            }
            driver.findElement(By.xpath(forgotBackBtn)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            test.log(LogStatus.INFO, "Step 7 : User is back at Login screen.");
        } else {
            test.log(LogStatus.WARNING, "Test Case has been Skipped because Internet is unavailable - " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }
    }

    @Test(priority = 14, groups = {"search"})
    public void Positive_Negative_Search() throws Exception {

        TestCaseName = objgetdata.GetData(0, 14, 1);
        TestCaseType = objgetdata.GetData(0, 14, 2);
        TestCaseDescription = objgetdata.GetData(0, 14, 3);
        test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Positive_Negative_Search");
        test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if (driver.getConnection() != AIRPLANE) {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 2 :Current Activity is verified as Login");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.findElement(By.xpath(editTextDynamic)).clear();
            driver.findElement(By.xpath(editPassDynamic)).clear();
            test.log(LogStatus.INFO, "Step 3 :UserName and Password cleared Successfully");
            driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("validEmail").trim());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(editPassDynamic)).sendKeys(properties.getProperty("validPassword").trim());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(loginbtn)).click();
            test.log(LogStatus.INFO, "Step 4 :Login button has been clicked Successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardActivity)));
            Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("dashboardActivity")));
            test.log(LogStatus.INFO, "Step 5 :Current Activity is verified as " + driver.currentActivity());
            Thread.sleep(Integer.parseInt(properties.getProperty("minMidWait")));
            TouchAction searchIcon = new TouchAction(driver);
            searchIcon.tap(810, 102).release().perform();
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            test.log(LogStatus.INFO, "Step 6 :Search icon has been clicked Successfully");
            driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("positiveSearchValue").trim());
            test.log(LogStatus.INFO, "Step 7 :Positive search text has been passed Successfully");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            TouchAction searchIconValid = new TouchAction(driver);
            searchIconValid.tap(945, 102).release().perform();
            test.log(LogStatus.INFO, "Step 8 :Search icon has been clicked Successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("midWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            int positiveSearchResult = driver.findElements(By.xpath(itemText)).size();
            Assert.assertEquals(positiveSearchResult, 8);
            test.log(LogStatus.INFO, "Step 9 :Search result has been verified Successfully");
            driver.findElement(By.xpath(plusIcon)).click();
            test.log(LogStatus.INFO, "Step 10 :Search item has been added to cart Successfully");
            driver.findElement(By.xpath(editTextDynamic)).clear();
            driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("negativeSearchValue").trim());
            test.log(LogStatus.INFO, "Step 11 :Negative search text has been passed Successfully");
            TouchAction searchIconInvalid = new TouchAction(driver);
            searchIconInvalid.tap(945, 102).release().perform();
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            test.log(LogStatus.INFO, "Step 12 :Search icon has been clicked Successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("midWait")));
            int negativeSearchResult = driver.findElements(By.xpath(itemText)).size();
            Assert.assertEquals(negativeSearchResult, 0);
            test.log(LogStatus.INFO, "Step 13 :Search result has been verified Successfully");
            driver.findElement(By.xpath(searchBackBtn)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("midWait")));
            Assert.assertEquals(Integer.parseInt(driver.findElement(By.xpath(cartItemsCount)).getText()), 1);
            test.log(LogStatus.INFO, "Step 14 :Item has been successfully added to cart");
            Thread.sleep(Integer.parseInt(properties.getProperty("midWait")));
            driver.findElement(By.xpath(dashboardMenu)).click();
            int x1 = driver.findElement(By.xpath(shareAppMenu)).getLocation().x;
            int y1 = driver.findElement(By.xpath(shareAppMenu)).getLocation().y;
            int x = driver.findElement(By.xpath(homeMenu)).getLocation().x;
            int y = driver.findElement(By.xpath(homeMenu)).getLocation().y;
            TouchAction actions2 = new TouchAction(driver);
            actions2.press(x1, y1).waitAction(Integer.parseInt(properties.getProperty("minWait"))).moveTo(x, y).release().perform();
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            test.log(LogStatus.INFO, "Step 15 :Menu item has been scrolled successfully");
            driver.findElement(By.xpath(logoutMenu)).click();
            test.log(LogStatus.INFO, "Step 16 :Application has been logged out successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 17 :Current Activity is verified as Login");
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.findElement(By.xpath(editTextDynamic)).clear();
            driver.findElement(By.xpath(editPassDynamic)).clear();
            test.log(LogStatus.INFO, "Step 18 :UserName and Password cleared Successfully");
            driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("validEmail").trim());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(editPassDynamic)).sendKeys(properties.getProperty("validPassword").trim());
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            driver.findElement(By.xpath(loginbtn)).click();
            test.log(LogStatus.INFO, "Step 19 :Login button has been clicked Successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardActivity)));
            Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("dashboardActivity")));
            test.log(LogStatus.INFO, "Step 20 :Current Activity is verified as " + driver.currentActivity());
            Thread.sleep(Integer.parseInt(properties.getProperty("minMidWait")));
            Assert.assertEquals(Integer.parseInt(driver.findElement(By.xpath(cartItemsCount)).getText()), 0);
            test.log(LogStatus.INFO, "Step 21 : Cart Item has been not remembered at reLogin - Its a defect");
            Thread.sleep(Integer.parseInt(properties.getProperty("minMidWait")));
            driver.findElement(By.xpath(dashboardMenu)).click();
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            TouchAction actions3 = new TouchAction(driver);
            actions3.press(x1, y1).waitAction(Integer.parseInt(properties.getProperty("minWait"))).moveTo(x, y).release().perform();
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            test.log(LogStatus.INFO, "Step 22 :Menu item has been scrolled successfully");
            driver.findElement(By.xpath(logoutMenu)).click();
            test.log(LogStatus.INFO, "Step 23 :Application has been logged out successfully");
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            Thread.sleep(Integer.parseInt(properties.getProperty("minWait")));
        } else {
            test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of " + driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }

    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            String errorPath = GetScreenshot.CaptureScreenshotForFailTestCase(driver, result.getName());
            String failedImage = test.addScreenCapture(errorPath);
            test.log(LogStatus.FAIL, "Failed", failedImage);
            driver.resetApp();
            Thread.sleep(5000);
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());

        }
        report.endTest(test);

    }

    @AfterTest(alwaysRun = true)
    public void End() throws Exception {
        reader.close();
        properties.clear();
        report.flush();
        objgetdata.closeFile();
        driver.quit();
        Thread.sleep(1000);

    }

    @AfterSuite(alwaysRun = true)
    public void sendEmail() throws Exception {
        Thread.sleep(5000);
        //SendEmail obj= new SendEmail();
        // obj.CreateEmail();
    }


}


