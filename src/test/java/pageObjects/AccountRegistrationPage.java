package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-firstname']")
    WebElement txtFirstName;

    @FindBy(xpath = "//input[@id='input-lastname']")
    WebElement txtLastName;

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmail;

    @FindBy(xpath = "//input[@id='input-telephone']")
    WebElement txtPhone;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtPassword;

    @FindBy(xpath = "//input[@id='input-confirm']")
    WebElement txtConfirmPassword;

    @FindBy(xpath = "//input[@name='agree']")
    WebElement radioBtnAgree;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnContinue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msgConfirmation;

    public void setTxtFirstName(String value) {
        txtFirstName.sendKeys(value);
    }

    public void setTxtLastName(String value) {
        txtLastName.sendKeys(value);
    }

    public void setTxtEmail(String value) {
        txtEmail.sendKeys(value);
    }

    public void setTxtPhone(String value) {
        txtPhone.sendKeys(value);
    }

    public void setTxtPassword(String value) {
        txtPassword.sendKeys(value);
    }

    public void setTxtConfirmPassword(String value) {
        txtConfirmPassword.sendKeys(value);
    }

    public void clickOnAgreeRadioBtn(){
        radioBtnAgree.click();
    }

    public void clickOnContinue(){
        btnContinue.click();

        //btnContinue.submit();

        //Actions act = new Actions(driver);
        //act.moveToElement(btnContinue).click().perform();

        //JavascriptExecutor js = (JavascriptExecutor)driver;
        //js.executeScript("argument[0].click()",btnContinue);

        //btnContinue.sendKeys(Keys.RETURN);

        //WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //myWait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
    }

    public String getConfirmationMsg(){
        try{
            return msgConfirmation.getText();
        }catch (Exception e){
            return e.getMessage();
        }
    }
}




