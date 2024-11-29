package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"sanity","master"})
    public void verify_account_registration() {
        try {
            logger.info("**** Starting TC001_AccountRegistrationTest ****");

            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            logger.info("Clicked on My Account Link....");

            hp.clickRegister();
            logger.info("Clicked on My Register Link....");

            logger.info("Providing User Details....");
            AccountRegistrationPage acp = new AccountRegistrationPage(driver);
            acp.setTxtFirstName(randomString(5).toUpperCase());
            acp.setTxtLastName(randomString(5).toUpperCase());
            acp.setTxtEmail(randomString(10).toUpperCase() + "@gmail.com");
            acp.setTxtPhone(randomNumber(10));
            String password = randomAlphaNumeric(10);
            acp.setTxtPassword(password);
            acp.setTxtConfirmPassword(password);
            acp.clickOnAgreeRadioBtn();
            acp.clickOnContinue();

            logger.info("Validating expected message....");
            String conMsg = acp.getConfirmationMsg();
            Assert.assertEquals(conMsg, "Your Account Has Been Created!");
        } catch (Exception e) {
            logger.error("Test Failed....");
            logger.debug("Debug logs....");
            Assert.fail();
        }

        logger.info("**** Finished TC001_AccountRegistrationTest ****");

    }

}
