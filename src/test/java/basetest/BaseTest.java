package basetest;

import config.AppProperties;
import config.DriverFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {

    protected static WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected static DriverFactory driverFactory;
    private static AppProperties appProperties;

    @BeforeAll
    static void beforeAll() {
        appProperties = AppProperties.getInstance();
        driverFactory = new DriverFactory();
        driver = driverFactory.getDriver();
        logger.info("<<<<Driver initialized>>>>");
        driver.get(System.getProperty("appUrl"));
    }

    @BeforeEach
    void beforeEach() {
        driver.get(System.getProperty("appUrl"));
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
        logger.debug("<<<<Driver closed properly>>>>");
    }
}
