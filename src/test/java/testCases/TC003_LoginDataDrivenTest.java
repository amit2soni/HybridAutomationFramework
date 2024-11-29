package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDataDrivenTest extends BaseClass {

    @Test(dataProvider = "loginData" , dataProviderClass = DataProviders.class,groups = "dataDriven")
    public void verify_loginDDT(String user , String pass , String res){
        logger.info("**** Starting TC002 LoginTest ****");

        logger.info("**** Go to login page ****");
        HomePage hp = new HomePage(driver);
        hp.clickMyAccount();
        hp.clickLogin();

        logger.info("Enter email ans password....");
        LoginPage lp = new LoginPage(driver);
        lp.setTxtEmail(user);
        lp.setTxtPassword(pass);
        lp.clickOnLoginBtn();


        MyAccountPage map = new MyAccountPage(driver);
        boolean targetPage = map.isMyAccountHeaderExists();

        if(res.equalsIgnoreCase("valid")){
            if(targetPage){
                hp.clickMyAccount();
                map.clickLogOut();
                Assert.assertTrue(true);
            }else{
                Assert.fail();
            }
        }
        if(res.equalsIgnoreCase("invalid")){
            if(targetPage){
                hp.clickMyAccount();
                map.clickLogOut();
                Assert.fail();
            }else{
                Assert.assertTrue(true);
            }
        }
        logger.info("TC002 LoginTest Finished");
    }

}
