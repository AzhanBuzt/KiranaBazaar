package com.example.meraparcel;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.lang3.RandomStringUtils;
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
    public String TestCaseName,TestCaseType,TestCaseDescription;
    private Properties properties;
    BufferedReader reader;
    private final String propertyFilePath= "../app/src/test/java/utility/Data.properties";
    int iteration=1;


    @BeforeTest(alwaysRun = true)
    public void setUp() throws Exception
    {
        //Loading Property Details
        reader = new BufferedReader(new FileReader(propertyFilePath));
        properties = new Properties();
        properties.load(reader);
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
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, uiAutomator2);
        capabilities.setCapability("autoGrantPermissions", t);
        capabilities.setCapability("autoAcceptAlerts", t);
        driver=new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        objgetdata=new DatafromExcel(properties.getProperty("excelSheetPath"));
        report= new ExtentReports(properties.getProperty("extentReportPath"),true);
        report.addSystemInfo("Extent Report Version", "2.05");
        report.addSystemInfo("Environment", "Test");
        report.config()
                .documentTitle("Kirana Bazaar Test Report")
                .reportName("Appium Report-")
                .reportHeadline("Test automation report of Kirana Bazaar by Aaqib")
                .insertCustomStyles(".test { border:2px solid #444; }");

        Thread.sleep(4000);
    }

    @Test(priority = 1, groups = { "ui" })
    public void LoginElementVisibilityCheck()
    {
        try {
                TestCaseName = objgetdata.GetData(0, 1, 1);
                TestCaseType = objgetdata.GetData(0, 1, 2);
                TestCaseDescription = objgetdata.GetData(0, 1, 3);
                test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "LoginElementVisibilityCheck");
                test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully");
            if( driver.getConnection()!=AIRPLANE) {
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
            }
            else
                {
                    test.log(LogStatus.WARNING, "Test Case has been Skipped because Internet is unavailable - "+driver.getConnection());
                    test.log(LogStatus.SKIP, "Test Case Skipped");
                }
        }
        catch (Exception e)
        {
            String ex=e.getMessage();
            System.out.println(ex);
            test.log(LogStatus.SKIP, "Test Case Skipped");
            driver.resetApp();
        }
    }

    @Test(priority=2,groups = { "functional"})
    public void Validate_LoginEmpty()
    {
    try {

            TestCaseName = objgetdata.GetData(0, 2, 1);
            TestCaseType = objgetdata.GetData(0, 2, 2);
            TestCaseDescription = objgetdata.GetData(0, 2, 3);
            test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_LoginEmpty");
            test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
        if(driver.getConnection()!=AIRPLANE) {
            //clearing email & password to make it blank
            driver.findElement(By.xpath(editTextDynamic)).clear();
            test.log(LogStatus.INFO, "Step 2 : Password Textbox is empty now.");
            driver.findElement(By.xpath(editPassDynamic)).clear();
            test.log(LogStatus.INFO, "Step 3 : Email Textbox is empty now.");
            //clicking the login btn
            Thread.sleep(500);
            driver.findElement(By.xpath(loginbtn)).click();
            test.log(LogStatus.INFO, "Step 4 : Button Login has been clicked successfully.");
            Thread.sleep(1000);
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            WebElement toastView = driver.findElement(By.xpath(toastMessage));
            test.log(LogStatus.INFO, "Step 5 : Toast Message text has been captured.");
            String es = toastView.getText();
            System.out.println(es);
            Assert.assertTrue(es.contains(properties.getProperty("verifyEmpty")));
            test.log(LogStatus.PASS, "Step 6 : Toast Text has been verified as " + es);

        }
        else{
            test.log(LogStatus.WARNING, "Test Case has been Skipped because Internet is unavailable - "+driver.getConnection());
            test.log(LogStatus.SKIP, "Test Case Skipped");
        }

        }
        catch (Exception e)
        {
            String ex=e.getMessage();
            System.out.println(ex);
            test.log(LogStatus.SKIP, "Test Case Skipped");
            driver.resetApp();
        }
}

    @Test(priority = 3,groups = { "functional" })
    public void Validate_ValidEmailEmptyPassword()
    {
        try {
            TestCaseName = objgetdata.GetData(0, 3, 1);
            TestCaseType = objgetdata.GetData(0, 3, 2);
            TestCaseDescription = objgetdata.GetData(0, 3, 3);
            test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_ValidEmailEmptyPassword");
            test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
            if(driver.getConnection()!=AIRPLANE ) {

                //clearing email & password to make it blank
                driver.findElement(By.xpath(editPassDynamic)).clear();
                test.log(LogStatus.INFO, "Step 2 : Password Textbox is empty now.");
                driver.findElement(By.xpath(editTextDynamic)).clear();
                test.log(LogStatus.INFO, "Step 3 : Email Textbox is empty now.");
                driver.findElement(By.xpath(editTextEmail)).sendKeys(properties.getProperty("validEmail"));
                test.log(LogStatus.INFO, "Step 4 : Valid Email has been inserted.");
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                //clicking the login btn
                Thread.sleep(500);
                driver.findElement(By.xpath(loginbtn)).click();
                test.log(LogStatus.INFO, "Step 4 : Button Login has been clicked successfully.");
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                WebElement toastView = driver.findElement(By.xpath(toastMessage));
                test.log(LogStatus.INFO, "Step 5 : Toast Message text has been captured.");
                String es = toastView.getText();
                System.out.println(es);
                Assert.assertTrue(es.contains(properties.getProperty("verifyWithEmail")));
                test.log(LogStatus.PASS, "Step 6 : Toast Text has been verified as " + es);
            }
            else{
                test.log(LogStatus.WARNING, "Test Case has been Skipped because Internet is unavailable - "+driver.getConnection());
                test.log(LogStatus.SKIP, "Test Case Skipped");
            }
        }
        catch (Exception e)
        {
            String ex=e.getMessage();
            System.out.println(ex);
            test.log(LogStatus.SKIP, "Test Case Skipped");
            driver.resetApp();
        }

    }

    @Test(priority=4,groups = { "functional" })
    public void Validate_EmailFormatWhileLogin()
    {
        try {

            TestCaseName = objgetdata.GetData(0, 4, 1);
            TestCaseType = objgetdata.GetData(0, 4, 2);
            TestCaseDescription = objgetdata.GetData(0, 4, 3);
            test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_EmailFormatWhileLogin");
            test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
            if(driver.getConnection()!=AIRPLANE) {
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
                    Thread.sleep(500);
                    driver.findElement(By.xpath(loginbtn)).click();
                    //clicking the login btn
                    test.log(LogStatus.INFO, "Step " + i + " : " + iteration + " iteration of login button has been clicked.");
                    Thread.sleep(2500);
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
            }
            else{
                test.log(LogStatus.WARNING, "Test Case has been Skipped because Internet is unavailable - "+driver.getConnection());
                test.log(LogStatus.SKIP, "Test Case Skipped");
            }
        }

        catch (Exception e)
        {
            String ex=e.getMessage();
            System.out.println(ex);
            test.log(LogStatus.SKIP, "Test Case Skipped");
            driver.resetApp();
        }

    }

    @Test(priority=5,groups = { "functional" })
    public void Validate_InternetConnectivity()
    {
        try{
    TestCaseName = objgetdata.GetData(0, 5, 1);
    TestCaseType = objgetdata.GetData(0, 5, 2);
    TestCaseDescription = objgetdata.GetData(0, 5, 3);
    test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_InternetConnectivity");
    test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
    if (driver.getConnection() != AIRPLANE) {

        test.log(LogStatus.PASS, "Step 2 : Current Connectivity is of "+driver.getConnection());
    }
    else{
        test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of "+driver.getConnection());
        test.log(LogStatus.SKIP, "Test Case Skipped");
    }}
    catch (Exception e)
    {
        String ex=e.getMessage();
        System.out.println(ex);
        test.log(LogStatus.SKIP, "Test Case Skipped");
        driver.resetApp();
    }
    }

    @Test(priority=6,groups = { "navigation" })
    public void Validate_Navigation_To_ForgotPassWordActivity()
    {
        try {
            TestCaseName = objgetdata.GetData(0, 6, 1);
            TestCaseType = objgetdata.GetData(0, 6, 2);
            TestCaseDescription = objgetdata.GetData(0, 6, 3);
            test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_Navigation_To_ForgotPassWordActivity");
            test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
            String currentActivity = driver.currentActivity();
            Assert.assertTrue(currentActivity.equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.INFO, "Step 2 :Current Activity is verified as Login");
            driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 20);
            WebElement ForgotLinkbtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
            test.log(LogStatus.INFO, "Step 3 :ForgotLink button is visible on Login Activity");
            Thread.sleep(1000);
            ForgotLinkbtn.click();
            test.log(LogStatus.INFO, "Step 4 :ForgotLink button is clicked Successfully");
            GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
            WebElement fActivity = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ForgotActivity)));
            test.log(LogStatus.INFO, "Step 5 :Forgot Activity is visible on App screen.");
            Thread.sleep(1000);
            driver.findElement(By.xpath(forgotBackBtn)).click();
            test.log(LogStatus.INFO, "Step 6 :Back button is clicked Successfully.");
            Assert.assertTrue(currentActivity.equals(properties.getProperty("loginActivity")));
            test.log(LogStatus.PASS, "Step 7 :User is back at Login Activity.");
        }
        catch (Exception e){
        String ex=e.getMessage();
        System.out.println(ex);
        test.log(LogStatus.SKIP, "Test Case Skipped");
        driver.resetApp();
    }}

    @Test(priority=7,groups = { "functional"})
    public void Validate_emptyEmailInForgotPassword()
    {
        try {

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
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                ForgotLinkbtn.click();
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                driver.findElement(By.xpath(editTextDynamic)).clear();
                test.log(LogStatus.INFO, "Step 5 :Forgot password has been cleared successfully.");
                driver.findElement(By.xpath(forgotBtn)).click();
                test.log(LogStatus.INFO, "Step 6 :Forgot button has been clicked successfully.");
                Thread.sleep(1000);
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
            }
            else{
                test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of "+driver.getConnection());
                test.log(LogStatus.SKIP, "Test Case Skipped");
            }
        }
        catch (Exception e){
            String ex=e.getMessage();
            System.out.println(ex);
            test.log(LogStatus.SKIP, "Test Case Skipped");
            driver.resetApp();
        }}

    @Test(priority=8,groups = { "functional"})
    public void Validate_EmailFormat_WhileForgotPassword()
    {
        try {
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
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                ForgotLinkbtn.click();
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                String generatedString = RandomStringUtils.randomAlphabetic(10);
                generatedString = generatedString.concat("#@gm.co.in");
                System.out.println(generatedString);
                Thread.sleep(1000);
                driver.findElement(By.xpath(editTextDynamic)).clear();
                test.log(LogStatus.INFO, "Step 4 :Forgot password has been cleared successfully.");
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                driver.findElement(By.xpath(editTextDynamic)).sendKeys(generatedString.trim());
                test.log(LogStatus.INFO, "Step 5 :Invalid Email has been set successfully.");
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                driver.findElement(By.xpath(forgotBtn)).click();
                test.log(LogStatus.INFO, "Step 6 :Forgot button has been clicked successfully.");
                Thread.sleep(1000);
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
            }
            else{
                test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of "+driver.getConnection());
                test.log(LogStatus.SKIP, "Test Case Skipped");
            }

        }
        catch (Exception e){
            String ex=e.getMessage();
            System.out.println(ex);
            test.log(LogStatus.SKIP, "Test Case Skipped");
            driver.resetApp();
        }
    }

    @Test(priority=9,groups = { "functional1"})
    public void Validate_EmailSentToUser_WhileForgotPassword()
    {
        try {
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
                wait = new WebDriverWait(driver, 20);
                WebElement ForgotLinkbtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
                test.log(LogStatus.INFO, "Step 3 :ForgotLink button is visible on Login Activity");
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                ForgotLinkbtn.click();
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                Thread.sleep(1000);
                driver.findElement(By.xpath(editTextDynamic)).clear();
                test.log(LogStatus.INFO, "Step 4 :Forgot password has been cleared successfully.");
                driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("validEmail").trim());
                test.log(LogStatus.INFO, "Step 5 :Valid Forgot Email has been set successfully.");
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                driver.findElement(By.xpath(forgotBtn)).click();
                test.log(LogStatus.INFO, "Step 6 :Forgot button has been clicked successfully.");
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                WebElement toastView = driver.findElement(By.xpath(toastMessage));
                test.log(LogStatus.INFO, "Step 7 : Toast Message text has been captured.");
                String es = toastView.getText();
                System.out.println(es);
                Assert.assertTrue(es.equals(properties.getProperty("forgotPassword")));
                test.log(LogStatus.PASS, "Step 8 : Toast Text has been verified as " + es);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                Thread.sleep(2500);
                driver.findElement(By.xpath(forgotBackBtn)).click();
                test.log(LogStatus.INFO, "Step 9 :Back button is clicked Successfully.");
                Thread.sleep(1000);
                Assert.assertTrue(currentActivity.equals(properties.getProperty("loginActivity")));
                test.log(LogStatus.INFO, "Step 10 :User is back at Login Activity.");
            }
            else
                {
                test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of "+driver.getConnection());
                test.log(LogStatus.SKIP, "Test Case Skipped");
            }
        }
        catch (Exception e){
            String ex=e.getMessage();
            System.out.println(ex);
            test.log(LogStatus.SKIP, "Test Case Skipped");
            driver.resetApp();
        }
    }

    @Test(priority=10,groups = { "login"})
    public void Validate_SuccessfulLogin()
    {
        try {
            TestCaseName = objgetdata.GetData(0, 10, 1);
            TestCaseType = objgetdata.GetData(0, 10, 2);
            TestCaseDescription = objgetdata.GetData(0, 10, 3);
            test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Validate_SuccessfulLogin");
            test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
            if (driver.getConnection() != AIRPLANE) {
                Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("loginActivity")));
                test.log(LogStatus.INFO, "Step 2 :Current Activity is verified as Login");
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                driver.findElement(By.xpath(editTextDynamic)).clear();
                driver.findElement(By.xpath(editPassDynamic)).clear();
                test.log(LogStatus.INFO, "Step 3 :UserName and Password cleared Successfully");
                driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("validEmail").trim());
                Thread.sleep(1000);
                driver.findElement(By.xpath(editPassDynamic)).sendKeys(properties.getProperty("validPassword").trim());
                Thread.sleep(1000);
                driver.findElement(By.xpath(loginbtn)).click();
                test.log(LogStatus.INFO, "Step 4 :Login button has been clicked Successfully");
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                wait = new WebDriverWait(driver, 20);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardActivity)));
                Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("dashboardActivity")));
                test.log(LogStatus.INFO, "Step 5 :Current Activity is verified as "+driver.currentActivity());
                Thread.sleep(1000);
                driver.findElement(By.xpath(dashboardMenu)).click();
                test.log(LogStatus.INFO, "Step 6 :Dashboard icon has been clicked successfully");
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                int x1= driver.findElement(By.xpath(shareAppMenu)).getLocation().x;
                int y1=driver.findElement(By.xpath(shareAppMenu)).getLocation().y;
                int x= driver.findElement(By.xpath(homeMenu)).getLocation().x;
                int y=driver.findElement(By.xpath(homeMenu)).getLocation().y;
                test.log(LogStatus.INFO, "Step 7 :Dynamic co-ordinates location has been fetched successfully");
                TouchAction actions= new TouchAction(driver);
                actions.press(x1,y1).waitAction(1000).moveTo(x,y).release().perform();
                test.log(LogStatus.INFO, "Step 8 :Menu item has been scrolled successfully");
                driver.findElement(By.xpath(logoutMenu)).click();
                test.log(LogStatus.PASS, "Step 9 :Application has been logged out successfully");
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
                Thread.sleep(1000);

            }
            else
                {
                test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of "+driver.getConnection());
                test.log(LogStatus.SKIP, "Test Case Skipped");
                }
        }
        catch (Exception e){
            String ex=e.getMessage();
            System.out.println(ex);
            test.log(LogStatus.SKIP, "Test Case Skipped");
            driver.resetApp();
        }

    }

    @Test(priority=11,groups = { "login"})
    public void Navigate_AllMenuItems()
    {
        try {
            TestCaseName = objgetdata.GetData(0, 11, 1);
            TestCaseType = objgetdata.GetData(0, 11, 2);
            TestCaseDescription = objgetdata.GetData(0, 11, 3);
            test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Navigate_AllMenuItems");
            test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
            if (driver.getConnection() != AIRPLANE) {
                wait = new WebDriverWait(driver, 20);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
                Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("loginActivity")));
                test.log(LogStatus.INFO, "Step 2 :Current Activity is verified as Login");
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                driver.findElement(By.xpath(editTextDynamic)).clear();
                driver.findElement(By.xpath(editPassDynamic)).clear();
                test.log(LogStatus.INFO, "Step 3 :UserName and Password cleared Successfully");
                driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("validEmail").trim());
                Thread.sleep(1000);
                driver.findElement(By.xpath(editPassDynamic)).sendKeys(properties.getProperty("validPassword").trim());
                Thread.sleep(1000);
                driver.findElement(By.xpath(loginbtn)).click();
                test.log(LogStatus.INFO, "Step 4 :Login button has been clicked Successfully");
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardActivity)));
                Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("dashboardActivity")));
                test.log(LogStatus.INFO, "Step 5 :Current Activity is verified as "+driver.currentActivity());
                Thread.sleep(1000);
                driver.findElement(By.xpath(dashboardMenu)).click();
                test.log(LogStatus.INFO, "Step 6 :Dashboard icon has been clicked successfully");
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                driver.findElement(By.xpath(homeMenu)).click();
                Thread.sleep(1000);
                test.log(LogStatus.INFO, "Step 7 :Home Menu has been clicked successfully");
                driver.findElement(By.xpath(dashboardMenu)).click();
                Thread.sleep(1000);
                driver.findElement(By.xpath(orderMenu)).click();
                test.log(LogStatus.INFO, "Step 8 :My Order Menu has been clicked successfully");
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                driver.findElement(By.xpath(dashboardMenu)).click();
                Thread.sleep(1000);
                driver.findElement(By.xpath(cartMenu)).click();
                test.log(LogStatus.INFO, "Step 9 :Cart Menu has been clicked successfully");
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                driver.findElement(By.xpath(dashboardMenu)).click();
                Thread.sleep(2000);
                driver.findElement(By.xpath(contactUsMenu)).click();
                Thread.sleep(2000);
                test.log(LogStatus.INFO, "Step 10 :Contact Us Menu has been clicked successfully");
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                TouchAction actions= new TouchAction(driver);
                actions.tap(150,72).waitAction(1000).perform().release();
                driver.findElement(By.xpath(dashboardMenu)).click();
                Thread.sleep(1000);
                driver.findElement(By.xpath(feedBackMenu)).click();
                test.log(LogStatus.INFO, "Step 11 :FeedBack Menu has been clicked successfully");
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                driver.findElement(By.xpath(dashboardMenu)).click();
                Thread.sleep(1000);
                driver.findElement(By.xpath(editProfileMenu)).click();
                test.log(LogStatus.INFO, "Step 12 :Edit Profile Menu has been clicked successfully");
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                driver.findElement(By.xpath(dashboardMenu)).click();
                int x1= driver.findElement(By.xpath(shareAppMenu)).getLocation().x;
                int y1=driver.findElement(By.xpath(shareAppMenu)).getLocation().y;
                int x= driver.findElement(By.xpath(homeMenu)).getLocation().x;
                int y=driver.findElement(By.xpath(homeMenu)).getLocation().y;
                TouchAction actions1= new TouchAction(driver);
                actions1.press(x1,y1).waitAction(2000).moveTo(x,y).release().perform();
                driver.findElement(By.xpath(shareAppMenu)).click();
                test.log(LogStatus.INFO, "Step 13 :Share App Menu has been clicked successfully");
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                Thread.sleep(2000);
                TouchAction actions2= new TouchAction(driver);
                actions2.tap(150,72).waitAction(1000).perform().release();
                driver.findElement(By.xpath(dashboardMenu)).click();
                Thread.sleep(1000);
                driver.findElement(By.xpath(notificationMenu)).click();
                test.log(LogStatus.INFO, "Step 14 :Notification Menu has been clicked successfully");
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                Thread.sleep(1000);
                driver.findElement(By.xpath(notificationBack)).click();
                Thread.sleep(1000);
                driver.findElement(By.xpath(logoutMenu)).click();
                test.log(LogStatus.PASS, "Step 15 :Logout Menu has been clicked successfully");
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
                Thread.sleep(1000);
            }
            else
            {
                test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of "+driver.getConnection());
                test.log(LogStatus.SKIP, "Test Case Skipped");
            }
        }
        catch (Exception e){
            String ex=e.getMessage();
            System.out.println(ex);
            test.log(LogStatus.SKIP, "Test Case Skipped");
            driver.resetApp();
        }

    }

    @Test(priority=12,groups = { "share"})
    public void Application_Share_Through_WhatsApp()
    {
        try {
            TestCaseName = objgetdata.GetData(0, 12, 1);
            TestCaseType = objgetdata.GetData(0, 12, 2);
            TestCaseDescription = objgetdata.GetData(0, 12, 3);
            test = report.startTest(TestCaseName, TestCaseDescription).assignCategory(TestCaseType, "Application_Share_Through_WhatsApp");
            test.log(LogStatus.INFO, "Step 1 :Test Case Started Successfully.");
            if (driver.getConnection() != AIRPLANE) {
                wait = new WebDriverWait(driver, 20);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
                Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("loginActivity")));
                test.log(LogStatus.INFO, "Step 2 :Current Activity is verified as Login");
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                driver.findElement(By.xpath(editTextDynamic)).clear();
                driver.findElement(By.xpath(editPassDynamic)).clear();
                test.log(LogStatus.INFO, "Step 3 :UserName and Password cleared Successfully");
                driver.findElement(By.xpath(editTextDynamic)).sendKeys(properties.getProperty("validEmail").trim());
                Thread.sleep(1000);
                driver.findElement(By.xpath(editPassDynamic)).sendKeys(properties.getProperty("validPassword").trim());
                Thread.sleep(1000);
                driver.findElement(By.xpath(loginbtn)).click();
                test.log(LogStatus.INFO, "Step 4 :Login button has been clicked Successfully");
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardActivity)));
                Assert.assertTrue(driver.currentActivity().equals(properties.getProperty("dashboardActivity")));
                test.log(LogStatus.INFO, "Step 5 :Current Activity is verified as "+driver.currentActivity());
                Thread.sleep(1000);
                driver.findElement(By.xpath(dashboardMenu)).click();
                test.log(LogStatus.INFO, "Step 6 :Dashboard icon has been clicked successfully");
                Thread.sleep(1000);
                driver.findElement(By.xpath(shareAppMenu)).click();
                test.log(LogStatus.INFO, "Step 7 :Share App Menu has been clicked successfully");
                Thread.sleep(3000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(whatsApp)));
                test.log(LogStatus.INFO, "Step 8 :Social Media Element WhatsApp has been found successfully");
                Thread.sleep(1000);
                driver.findElement(By.xpath(whatsApp)).click();
                test.log(LogStatus.INFO, "Step 9 :WhatsApp has been clicked successfully");
                Thread.sleep(1000);
                TouchAction actions= new TouchAction(driver);
                actions.tap(50,567).release().perform();
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                test.log(LogStatus.INFO, "Step 10 :Most frequent contacted on whatsApp has been clicked successfully");
                Thread.sleep(2000);
                driver.findElement(By.xpath(whatsAppImageButton)).click();
                test.log(LogStatus.INFO, "Step 10 :WhatsApp Send has been clicked successfully");
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                System.out.println(driver.findElement(By.xpath(editTextDynamic)).getText());
                Thread.sleep(1000);
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                driver.findElement(By.xpath(whatsAppImageButton)).click();
                test.log(LogStatus.PASS, "Step 11 :Share Link has been shared successfully");
                driver.pressKeyCode(AndroidKeyCode.BACK);
                Thread.sleep(1000);
                driver.pressKeyCode(AndroidKeyCode.BACK);
                test.log(LogStatus.INFO, "Step 12 :Focus has been moved to Kirana Bazaar from WhatsApp successfully");
                driver.findElement(By.xpath(dashboardMenu)).click();
                int x1= driver.findElement(By.xpath(shareAppMenu)).getLocation().x;
                int y1=driver.findElement(By.xpath(shareAppMenu)).getLocation().y;
                int x= driver.findElement(By.xpath(homeMenu)).getLocation().x;
                int y=driver.findElement(By.xpath(homeMenu)).getLocation().y;
                TouchAction actions2= new TouchAction(driver);
                actions2.press(x1,y1).waitAction(1000).moveTo(x,y).release().perform();
                GetScreenshot.CaptureScreenshotForPassTestCase(driver, TestCaseName);
                test.log(LogStatus.INFO, "Step 13 :Menu item has been scrolled successfully");
                driver.findElement(By.xpath(logoutMenu)).click();
                test.log(LogStatus.INFO, "Step 14 :Application has been logged out successfully");
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(forgotPasswordLink)));
                Thread.sleep(1000);
             }
            else
            {
                test.log(LogStatus.INFO, "Step 2 : Current Connectivity is of "+driver.getConnection());
                test.log(LogStatus.SKIP, "Test Case Skipped");
            }
        }
        catch (Exception e){
            String ex=e.getMessage();
            System.out.println(ex);
            test.log(LogStatus.SKIP, "Test Case Skipped");
            driver.resetApp();
        }

    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result) throws Exception
    {
        if(result.getStatus()==ITestResult.FAILURE)
        {

            String errorPath=GetScreenshot.CaptureScreenshotForFailTestCase(driver,result.getName());
            String failedImage=test.addScreenCapture(errorPath);
            test.log(LogStatus.FAIL, "Failed", failedImage);
        }
        else if(result.getStatus()==ITestResult.SKIP)
        {
            test.log(LogStatus.SKIP, "Test Case Skipped is "+result.getName());

        }

        report.endTest(test);

    }

    @AfterTest(alwaysRun = true)
    public void End() throws Exception
    {
        reader.close();
        properties.clear();
        report.flush();
        objgetdata.closeFile();
        driver.quit();
        Thread.sleep(1000);

    }

    @AfterSuite(alwaysRun = true)
    public void sendEmail() throws Exception
    {
        //Thread.sleep(5000);
       // SendEmail obj= new SendEmail();
       // obj.CreateEmail();
    }





}


