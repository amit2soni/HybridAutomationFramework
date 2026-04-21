package testBase;

import com.epam.healenium.SelfHealingDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class BaseClass {
    public static final String USERNAME = "amitkumarsoni_NxbrgB";
    public static final String AUTOMATE_KEY = "JkizspER4GrwS4JiWwxU";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
    protected WebDriver driver;
    protected SelfHealingDriver selfHealingDriver;
    public Logger logger;
    public Properties prop;

    @BeforeClass(groups = {"sanity", "master", "regression"})
    @Parameters({"os", "browser"})
    public void setUp(String os, String br, ITestContext context) throws IOException {

        FileReader file = new FileReader("./src//test//resources//config.properties");
        prop = new Properties();
        prop.load(file);

        logger = LogManager.getLogger(this.getClass());
        if (prop.getProperty("execution_env").equalsIgnoreCase("browserstack")) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", br);
            caps.setCapability("browserVersion", "latest");

            HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
            browserstackOptions.put("os", "Windows");
            browserstackOptions.put("osVersion", "11");
            browserstackOptions.put("projectName", "Open Cart");
            browserstackOptions.put("buildName", "Build_1");  // Group tests under one build
            browserstackOptions.put("sessionName", context.getName());
            caps.setCapability("bstack:options", browserstackOptions);

            driver = new DriverFactory("remote",logger,URL,caps).createDriver();
        } else if (prop.getProperty("execution_env").equalsIgnoreCase("grid")) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("browserName", br);
            caps.setCapability("browserVersion", "latest");
            driver =  new DriverFactory("remote",logger,"http://localhost:4444",caps).createDriver();

        } else if (prop.getProperty("execution_env").equalsIgnoreCase("githubactions")) {
            driver = new DriverFactory("headlesschrome",logger).createDriver();
        }else if (prop.getProperty("execution_env").equalsIgnoreCase("local")) {
            driver = new DriverFactory("chrome",logger).createDriver();
           selfHealingDriver = SelfHealingDriver.create(driver);
        }
        if(prop.getProperty("self_heal").equalsIgnoreCase("yes")){
            selfHealingDriver.manage().deleteAllCookies();
            selfHealingDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            selfHealingDriver.get(prop.getProperty("appURL"));
            selfHealingDriver.manage().window().maximize();
        }else {
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get(prop.getProperty("appURL"));
            driver.manage().window().maximize();
        }
    }

    @AfterClass(groups = {"sanity", "master", "regression"})
    public void tearDown() {
        driver.quit();
    }

    public String randomString(int length) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .withinRange('A', 'Z')
                .build();
        return generator.generate(length);
    }

    public String randomNumber(int length) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', '9')
                .build();
        return generator.generate(length);
    }

    public String randomAlphaNumeric(int length) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', '9')
                .withinRange('a', 'z')
                .withinRange('A', 'Z')
                .build();
        return generator.generate(length);
    }

    public String captureScreen(String tname) {
        if (driver == null) {
            System.out.println("Driver is null - screenshot skipped");
            return null;
        }
        try {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

            String targetFilePath = System.getProperty("user.dir")
                    + "/screenshots/" + tname + "_" + timeStamp + ".png";
            File targetFile = new File(targetFilePath);

            sourceFile.renameTo(targetFile);

            return targetFilePath;
        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
            return null;
        }
    }
}
