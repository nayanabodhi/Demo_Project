package Tests;

import Base.BaseClass;
import Base.DriverFactory;
import Pages.AppointmentPage;
import Pages.HomePage;
import Pages.LoginPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class LoginPageTest extends BaseClass{
    public static String userName;
    public static String passWord;
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;

    @BeforeTest
    public void extentReportSetup() {
        //location of the extent report
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
        extent = new ExtentReports();  //create object of ExtentReports
        extent.attachReporter(htmlReporter);

        htmlReporter.config().setDocumentTitle("Automation Report"); // Tittle of Report
        htmlReporter.config().setReportName("Extent Report V4"); // Name of the report
        htmlReporter.config().setTheme(Theme.DARK);//Default Theme of Report

        // General information releated to application
        extent.setSystemInfo("Application Name", "http://demoaut.katalon.com/");
        extent.setSystemInfo("User Name", "Nayana Bodhi");
        extent.setSystemInfo("Envirnoment", "Test");
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }
    @Test
    public void loginTestWithCorrectCredentials() throws InterruptedException {
        test = extent.createTest("login Test With Correct Credentials");
        HomePage hp = new HomePage();
        LoginPage lp = new LoginPage();
        AppointmentPage ap = new AppointmentPage();

        openBrowser();

        //Verify page title
        Assert.assertEquals(DriverFactory.driver.getTitle(),"CURA Healthcare Service");


        hp.bookAppointmentAction();
        //Verify Login header
        Assert.assertEquals(ap.getHeader(),"Login");

        //Get user credentials
        try {
            getUserCredentials();
        } catch (YamlException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Login with the user credentials
        lp.Login(userName,passWord);

        // Verify Page Navigation
        Assert.assertEquals(DriverFactory.driver.getTitle(),"CURA Healthcare Service");

        //Perform Book Appointment
        ap.bookingAppointment();

        // Verify Appointment Confirmation
        Assert.assertEquals(ap.getHeader(),"Appointment Confirmation");

        //Logout Step
        ap.logout();

        //Verify page after Logout
        Assert.assertEquals(hp.bookAppointmentBtn.getText(),"Make Appointment");

       //Close the browser
        closeBrowser();
    }
    @Test
    public void loginTestWithIncorrectCredentials() throws InterruptedException {
        test = extent.createTest("login Test With Incorrect Credentials");
        HomePage hp = new HomePage();
        LoginPage lp = new LoginPage();
        AppointmentPage ap = new AppointmentPage();

        openBrowser();

        //Verify page title
        Assert.assertEquals(DriverFactory.driver.getTitle(),"CURA Healthcare Service");

        hp.bookAppointmentAction();
        //Verify Login header
        Assert.assertEquals(ap.getHeader(),"Login");


        //Login with the user incorrect credentials
        lp.Login("Wrong_Username","Wrong_Password");

        Thread.sleep(5000);
        // Verify Page Navigation
        Assert.assertTrue(DriverFactory.driver.findElement(By.xpath("//p[@class='lead text-danger']")).getText().contains("Login failed!...blahhh"));

        //Close the browser
        closeBrowser();
    }

    @AfterMethod
    public void getResult(ITestResult result) throws Exception
    {
        if(result.getStatus() == ITestResult.FAILURE)
        {
            //MarkupHelper is used to display the output in different colors
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));

            //To capture screenshot path and store the path of the screenshot in the string "screenshotPath"
            //We do pass the path captured by this method in to the extent reports using "logger.addScreenCapture" method.

            //	String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
            String screenshotPath = TakeScreenshot(DriverFactory.driver, result.getName());
            //To add it in the extent report

            test.fail("Test Case Failed Snapshot is below " + test.addScreenCaptureFromPath(screenshotPath));


        }
        else if(result.getStatus() == ITestResult.SKIP){
            //logger.log(Status.SKIP, "Test Case Skipped is "+result.getName());
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }
        DriverFactory.driver.quit();
    }

    public static String TakeScreenshot(WebDriver driver, String screenshotName) throws IOException {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    public void getUserCredentials() throws YamlException, FileNotFoundException {
        YamlReader reader = new YamlReader(new FileReader(System.getProperty("user.dir")+"\\src\\test\\java\\Configuration\\Users.yaml"));
        Object object = reader.read();
        System.out.println(object);
        Map map = (Map)object;
        userName = (map.get("username")).toString();
        passWord = (map.get("password")).toString();
    }

}
