package config;

import org.openqa.selenium.WebDriver;

public class DriverFactory {

    private WebDriver driver;
    private String browserName;

    public WebDriver getDriver(){


        return driver;
    }
}
