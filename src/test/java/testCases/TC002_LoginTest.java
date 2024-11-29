package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

    @Test(groups = {"regression","master"})
    public void verify_login() {

        try {
            logger.info("**** Starting TC002 LoginTest ****");

            logger.info("**** Go to login page ****");
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();

            logger.info("Enter email ans password....");
            LoginPage lp = new LoginPage(driver);
            lp.setTxtEmail(prop.getProperty("email"));
            lp.setTxtPassword(prop.getProperty("password"));
            lp.clickOnLoginBtn();


            MyAccountPage map = new MyAccountPage(driver);
            boolean targetPage = map.isMyAccountHeaderExists();

            Assert.assertEquals(targetPage, true, "Login Failed");
            logger.info("TC002 LoginTest Finished");
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
