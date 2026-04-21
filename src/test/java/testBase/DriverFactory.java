package testBase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private String browser;
    private Logger log;
    private String url;
    private DesiredCapabilities caps;

    public DriverFactory(String browser,Logger log) {
        this.browser = browser;
        this.log = log;
    }
    public DriverFactory(String browser, Logger log, String url, DesiredCapabilities caps) {
        this.browser = browser;
        this.log = log;
        this.url = url;
        this.caps = caps;
    }

    public WebDriver createDriver() throws MalformedURLException {
        log.info("Creating driver "+browser);

        switch (browser){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
                break;
            case "headlesschrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                driver.set(new ChromeDriver(options));
                break;
            case "remote":
                driver.set(new RemoteWebDriver(new URL(url),caps));
                break;
            default:
                log.info("Browser not supported");

        }
        return driver.get();
    }

}
