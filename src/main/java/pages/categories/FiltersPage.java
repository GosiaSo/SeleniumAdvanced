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

    @FindBy(xpath = "//div[contains(@class, 'ui-slider')]/a[2]")
    private WebElement rightSlider;

    @FindBy(css = "#_desktop_search_filters_clear_all button")
    private WebElement clearButton;

    @FindBy(css = "#js-active-search-filters.active_filters")
    private WebElement activeFiltersLabel;

    @FindBy(css = "ul[data-slider-label='Price'] li p")
    private WebElement rangeFilterLabel;

    public int getMaxRangeFilter() {
        String text = rangeFilterLabel.getText().trim();
        String substring = text.substring(text.lastIndexOf("$") + 1, text.lastIndexOf(("."))).trim();
        return Integer.parseInt(substring);
    }

    public void slidePriceRightSlider(int maxPrice) {
        int maxRange = getMaxRangeFilter();
        int percent = (maxRange - maxPrice);
        actions.clickAndHold(rightSlider);
        for (int i = 0; i < percent; i++) {
            actions.keyDown(Keys.LEFT);
        }
        actions.release();
        actions.perform();
        waitToBeVisible(activeFiltersLabel);
    }

    public void clearFilters() {
        waitToBeClickable(clearButton);
        click(clearButton);
        logger.info("Cleaning filters.");
        waitForInvisibilityOf(activeFiltersLabel);
    }
}
