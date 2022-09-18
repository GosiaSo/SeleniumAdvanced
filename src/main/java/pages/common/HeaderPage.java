package pages.common;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.PageBase;

public class HeaderPage extends PageBase {

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#search_widget [type=\"text\"]")
    private WebElement searchInput;

    @FindBy(css = "#search_widget button")
    private WebElement submitSearchButton;


    public void useSearchEngine(String searchText) {
        sendKeysWithClear(searchInput, searchText);
        actions.keyDown(Keys.ENTER).perform();
    }
}
