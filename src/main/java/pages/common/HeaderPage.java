package pages.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

import java.util.List;

public class HeaderPage extends PageBase {

    private static final Logger logger = LoggerFactory.getLogger(HeaderPage.class);

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a[data-depth='0']")
    private List<WebElement> mainCategoriesMenu;

    @FindBy(css = "a[data-depth='1']")
    private List<WebElement> additionalCategoryMenuItems;
    @FindBy(css = ".top-menu")
    private WebElement menu;


    public List<WebElement> getMainCategoriesMenuItems() {
        waitToBeVisible(menu);
        return mainCategoriesMenu;
    }

    public List<WebElement> getSecondLevelMenuItems() {
        return additionalCategoryMenuItems;
    }

    public void selectCategory(WebElement element) {
        click(element);
    }

    public String getNameOfMenuElement(WebElement element) {
        waitToBeVisible(element);
        return element.getText();
    }
}
