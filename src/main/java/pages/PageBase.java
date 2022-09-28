package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class PageBase {
    private static final Logger logger = LoggerFactory.getLogger(PageBase.class);

    public PageBase(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public WebDriver driver;
    public Actions actions;
    public WebDriverWait wait;

    public String getNameOfProduct(WebElement element) {
        return element.findElement(By.cssSelector("a")).getAttribute("textContent");
    }

    public void waitToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForInvisibilityOf(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void click(WebElement element) {
        logger.info("Clicking on: " + element.getText());
        element.click();
    }

    public void sendKeys(WebElement element, String text) {
        logger.info("Typing: " + text);
        element.sendKeys(text);
    }

    public void sendKeysWithClear(WebElement element, String text) {
        logger.info("Clearing text: ");
        element.clear();

        if (element.getText().length() != 0) {
            element.sendKeys(Keys.ALT + "a" + Keys.DELETE);
        }
        sendKeys(element, text);
    }

    public WebElement getRandomElement(List<WebElement> elements) {
        return elements.get(new Random().nextInt(elements.size()));
    }

    public double getPriceFromText(String text){
        String substring = text.substring(text.indexOf("$") + 1).trim();
        return Double.parseDouble(substring);
    }
}
