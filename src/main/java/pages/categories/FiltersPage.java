package pages.categories;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

import java.util.List;

public class FiltersPage extends PageBase {

    private static final Logger logger = LoggerFactory.getLogger(FiltersPage.class);

    public FiltersPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "p.facet-title")
    private List<WebElement> facets;

    @FindBy(xpath = "//div[contains(@class, 'ui-slider')]/a[1]")
    private WebElement leftSlider;

    @FindBy(xpath = "//div[contains(@class, 'ui-slider')]/a[2]")
    private WebElement rightSlider;

    @FindBy(css = "#_desktop_search_filters_clear_all button")
    private WebElement clearButton;


    public List<WebElement> getAllFacets() {
        return facets;
    }

    public WebElement getFacet(String facet) {
        for (int i = 0; i < facets.size(); i++) {
            if (facets.get(i).getText().equalsIgnoreCase(facet)) {
                logger.info("Found facet element: " + facet);
                return facets.get(i);
            }
        }
        return null;
    }

    public void slidePriceRightSlider(int maxPrice) {
        int minRange = 9;
        int maxRange = 29;
        int percent = (maxRange - maxPrice);
        actions.clickAndHold(rightSlider);
        for (int i = 0; i < percent; i++) {
            actions.keyDown(Keys.LEFT);
        }
        actions.release();
        actions.perform();

    }

    public void clearFilters(){
        waitToBeClickable(clearButton);
        click(clearButton);
    }
}
