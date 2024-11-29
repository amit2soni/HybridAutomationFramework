package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmail;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtPassword;

    @FindBy(xpath = "//input[@value='Login']")
    WebElement btnLogin;

    public void setTxtEmail(String value) {
        txtEmail.sendKeys(value);
    }

    public void setTxtPassword(String value) {
        txtPassword.sendKeys(value);
    }

    public void clickOnLoginBtn(){
        btnLogin.click();
    }
}

