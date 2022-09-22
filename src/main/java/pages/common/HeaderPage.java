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

    @FindBy(css = "#category-3 a")
    private WebElement clothesMainCategory;

    @FindBy(css = "#category-6 a")
    private WebElement accessoriesMainCategory;

    @FindBy(css = "li#category-4 a")
    private WebElement menSubcategory;

    @FindBy(css = "li#category-5 a")
    private WebElement womenSubcategory;

    @FindBy(css = "li#category-7 a")
    private WebElement stationerySubcategory;

    @FindBy(css = "li#category-8 a")
    private WebElement homeAccessoriesSubcategory;

    public List<WebElement> getMainCategoriesMenuItems() {
        waitToBeVisible(menu);
        return mainCategoriesMenu;
    }

    public List<WebElement> getSecondLevelMenuItems() {
        return additionalCategoryMenuItems;
    }

    public WebElement getClothesMainCategory() {
        return clothesMainCategory;
    }

    public WebElement getAccessoriesMainCategory() {
        return accessoriesMainCategory;
    }

    public WebElement getMenSubcategory() {
        return menSubcategory;
    }

    public WebElement getWomenSubcategory() {
        return womenSubcategory;
    }

    public WebElement getStationerySubcategory() {
        return stationerySubcategory;
    }

    public WebElement getHomeAccessoriesSubcategory() {
        return homeAccessoriesSubcategory;
    }

    public void selectCategory(WebElement element) {
        click(element);
    }

    public void selectCategory(String category) {
        for (int i = 0; i < mainCategoriesMenu.size(); i++) {
            if (mainCategoriesMenu.get(i).getText().equalsIgnoreCase(category)) {
                click(mainCategoriesMenu.get(i));
            }
        }
    }

    public String getNameOfMenuElement(WebElement element) {
        return element.getText();
    }

    public String getNameOfSubCategoryElement(WebElement element) {
        return element.getAttribute("innerHTML").trim();
    }

    public void clickAdditionalCategory(WebElement mainCategory, WebElement additionalCategory) {
        waitToBeVisible(menu);
        waitToBeVisible(mainCategory);

        actions.moveToElement(mainCategory).build().perform();
        waitToBeVisible(additionalCategory);
        click(additionalCategory);
    }
}
