package Pages;

import Base.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    public HomePage(){
        PageFactory.initElements(DriverFactory.driver,this);
    }
    @FindBy(how = How.ID,using = "btn-make-appointment")
    public WebElement bookAppointmentBtn;

    public void bookAppointmentAction() throws InterruptedException {
        bookAppointmentBtn.click();
    }
}
