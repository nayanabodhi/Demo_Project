package Pages;

import Base.BaseClass;
import Base.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentPage {
    public AppointmentPage() {
        PageFactory.initElements(DriverFactory.driver, this);
    }

    @FindBy(how = How.XPATH,using = "//select[@id='combo_facility']/option[@value='Hongkong CURA Healthcare Center']")
    public WebElement facilityDD;

    @FindBy(how = How.XPATH,using = "//input[@value='Medicare']")
    public  WebElement healthProgramRadioBtn_Medicare;

    @FindBy(how = How.XPATH,using = "//input[@value='Medicaid']")
    public  WebElement healthProgramRadioBtn_Medicaid;

    @FindBy(how = How.XPATH,using = "//input[@value='None']")
    public  WebElement healthProgramRadioBtn_None;

    @FindBy(how = How.ID,using= "txt_visit_date")
    public WebElement visitDateInput;

    @FindBy(how = How.XPATH,using = "//textarea[@id='txt_comment']")
    public WebElement commentsAreaTxt;

    @FindBy(how = How.ID,using = "btn-book-appointment")
    public WebElement bookAppointmentBtn;

    DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
    Date date = new Date();
    public String currentDate = dateFormat.format(date);

    @FindBy(how = How.ID,using = "menu-toggle")
    public WebElement menuToggleBtn;

    @FindBy(how = How.LINK_TEXT,using = "Logout")
    public WebElement logoutLink;


    public void bookingAppointment(){
       // PageFactory.initElements(driver,this);
        facilityDD.click();
        healthProgramRadioBtn_Medicare.click();
        visitDateInput.sendKeys(currentDate);
        commentsAreaTxt.sendKeys("Kindly book any appointment in the second half of the day");
        bookAppointmentBtn.click();
    }
    public void closeBrowser(){

        DriverFactory.driver.quit();
    }

    public void logout() throws InterruptedException {
       // PageFactory.initElements(this.driver,this);
        menuToggleBtn.click();
        Thread.sleep(5000);
        logoutLink.click();

    }

    public String getHeader(){
        WebElement appointmentConfirmHeader = DriverFactory.driver.findElement(By.tagName("h2"));
        return appointmentConfirmHeader.getText();
    }

}
