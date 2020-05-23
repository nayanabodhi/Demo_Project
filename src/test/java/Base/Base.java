package Base;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Base {


    static Properties config;
    static FileInputStream fis;
    public static String URL;
    public static String Browser_type;


    public static void getUrl(){
        config = new Properties();
        try {
            fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\Configuration\\config.properties");
            config.load(fis);
            URL = config.getProperty("url");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static WebDriver driverInitialise() {
        config = new Properties();
        try {
            fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\Configuration\\config.properties");
            config.load(fis);
            URL = config.getProperty("url");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Browser_type = config.getProperty("browser");
        switch (Browser_type) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\java\\Resources\\chromedriver_win32\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setPageLoadStrategy(PageLoadStrategy.EAGER);
                DriverFactory.driver = new ChromeDriver(options);
                break;
            case "FF": //not implemented
                break;
            case "IE"://not implemented
                break;

        }
        DriverFactory.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        DriverFactory.driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        return DriverFactory.driver;

    }
}
