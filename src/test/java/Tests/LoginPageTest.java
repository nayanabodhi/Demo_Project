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

    @Test
    public void loginTestWithCorrectCredentials() throws InterruptedException {
        test = extent.createTest("login Test With Correct Credentials");
        HomePage hp = new HomePage();
        LoginPage lp = new LoginPage();
        AppointmentPage ap = new AppointmentPage();

        //Open the Browser and Navigate to the URL
        openBrowser();

        //Verify page title
        Assert.assertEquals(DriverFactory.driver.getTitle(),"CURA Healthcare Service");

        hp.bookAppointmentAction();
        //Verify Login header
        Assert.assertEquals(ap.getHeader(),"Login");

        //Get user credentials
        try {
            getUserCredentials();
        } catch (YamlException | FileNotFoundException e) {
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

        //Open the Browser and Navigate to the URL
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
        Assert.assertTrue(DriverFactory.driver.findElement(By.xpath("//p[@class='lead text-danger']")).getText().contains("Login failed!"));

        //Close the browser
        closeBrowser();
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
