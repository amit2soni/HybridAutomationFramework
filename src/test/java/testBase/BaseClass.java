package testBase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
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
    public static WebDriver driver;
    public Logger logger;
    public Properties prop;

    @BeforeClass(groups = {"sanity", "master", "regression"})
    @Parameters({"os", "browser"})
    public void setUp(String os, String br, ITestContext context) throws IOException {

        FileReader file = new FileReader("./src//test//resources//config.properties");
        prop = new Properties();
        prop.load(file);

        logger = LogManager.getLogger(this.getClass());
        if (prop.getProperty("execution_env").equalsIgnoreCase("bstack")) {
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

            driver = new RemoteWebDriver(new URL(URL), caps);
        } else if (prop.getProperty("execution_env").equalsIgnoreCase("headless")) {

            ChromeOptions options = new ChromeOptions();

            options.addArguments("--no-sandbox");

            options.addArguments("--disable-dev-shm-usage");

            options.addArguments("--headless");

            WebDriverManager.chromedriver().setup();

        } else if (prop.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (br) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    System.out.println("Invalid Browser Name");
                    return;
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(prop.getProperty("appURL"));
        driver.manage().window().maximize();
    }

    @AfterClass(groups = {"sanity", "master", "regression"})
    public void tearDown() {
        driver.quit();
    }

    public String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public String randomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public String randomAlphaNumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
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
