package pages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

import java.util.ArrayList;
import java.util.List;

public class SearchEnginePage extends PageBase {

    private static final Logger logger = LoggerFactory.getLogger(SearchEnginePage.class);
    public SearchEnginePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#search_widget [type=\"text\"]")
    private WebElement searchInput;

    @FindBy(css = "#search_widget button")
    private WebElement submitSearchButton;

    @FindBy(css = ".ui-autocomplete li ")
    private List<WebElement> autocompleteList;


    public void useSearchEngine(String searchText) {
        sendKeysWithClear(searchInput, searchText);
    }

    public void enterSearch() {
        actions.keyDown(Keys.ENTER).perform();
    }

    public List<String> retrieveAutocompleteSearchItems(String searchText) {
        List<String> autocompleteSearchItems = new ArrayList<>();
        useSearchEngine(searchText);
        for (WebElement webElement : autocompleteList) {
            waitToBeClickable(webElement);
            String dropdownItem = webElement.findElement(By.cssSelector("span.product")).getText();
            autocompleteSearchItems.add(dropdownItem);
            logger.debug(dropdownItem);
        }
        return autocompleteSearchItems;
    }
}
