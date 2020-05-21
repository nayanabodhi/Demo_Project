package Base;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.Properties;

public class BaseClass extends Base{
   public static Properties prop;
   public static String Browser_type = null;

   @BeforeMethod
   public void Initialize(){
      getUrl();
      driverInitialise();
   }

   public void openBrowser(){
      DriverFactory.driver.manage().window().maximize();
      DriverFactory.driver.navigate().to(URL);
      WebDriverWait wait = new WebDriverWait(DriverFactory.driver, 5000);
   }
   public void closeBrowser(){
      DriverFactory.driver.close();
   }

   @AfterMethod
   public void tearDown() throws  IOException {
      DriverFactory.driver.quit();
      fis.close();
   }
}
