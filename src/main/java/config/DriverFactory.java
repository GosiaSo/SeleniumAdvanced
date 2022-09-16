package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private WebDriver driver;
    private String browserName;

    public WebDriver getDriver(){
        this.browserName = System.getProperty("browserName");
        logger.info("BrowserName: " + browserName);
        switch (this.browserName) {
            case "chrome" -> {
                ChromeOptions optionsChrome = new ChromeOptions();
                WebDriverManager.chromedriver().setup();
                optionsChrome.addArguments("start-maximized");
                driver = new ChromeDriver(optionsChrome);
                driver.get(System.getProperty("appUrl"));
            }
            case "firefox" -> {
                FirefoxOptions optionsFirefox = new FirefoxOptions();
                WebDriverManager.firefoxdriver().setup();
                optionsFirefox.addArguments("start-maximized");
                driver = new FirefoxDriver(optionsFirefox);
                driver.get(System.getProperty("appUrl"));
            }
            case "edge" -> {
                EdgeOptions optionsEdge = new EdgeOptions();
                WebDriverManager.firefoxdriver().setup();
                optionsEdge.addArguments("start-maximized");
                driver = new EdgeDriver(optionsEdge);
                driver.get(System.getProperty("appUrl"));
            }
            case "ie" -> {
                InternetExplorerOptions optionsExplorer = new InternetExplorerOptions();
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver(optionsExplorer);
                driver.get(System.getProperty("appUrl"));
            }
        }
        return this.driver;
    }
}
