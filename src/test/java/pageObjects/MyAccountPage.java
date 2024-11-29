package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{
    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    WebElement myAccountHeader;

    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Logout']")
    WebElement linkLogOut;

    public boolean isMyAccountHeaderExists(){
        try{
            return myAccountHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogOut(){
        linkLogOut.click();
    }
}
