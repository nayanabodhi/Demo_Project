package Pages;

import Base.BaseClass;
import Base.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage() {
        PageFactory.initElements(DriverFactory.driver, this);
    }

    @FindBy(how = How.CLASS_NAME,using = "btn btn-dark btn-lg toggle")
    public WebElement menuToggleBtn;

    @FindBy(how =How.LINK_TEXT, using = "Login")
    public WebElement loginOption;

    @FindBy(how = How.ID,using = "txt-username")
    public WebElement usernameTxt;

    @FindBy(how = How.ID,using = "txt-password")
    public WebElement passwordTxt;

    @FindBy(how = How.ID,using = "btn-login")
    public WebElement loginBtn;


    public void Login(String userName,String passWord){
        usernameTxt.sendKeys(userName);
        passwordTxt.sendKeys(passWord);
        loginBtn.submit();
    }

}
