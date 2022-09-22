package pages.categories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

import java.util.List;

public class CategoryPage extends PageBase {

    private static final Logger logger = LoggerFactory.getLogger(CategoryPage.class);

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#js-product-list-header h1")
    private WebElement categoryTitle;
    @FindBy(css = "#search_filters .facet")
    private List<WebElement> facets;

    @FindBy(css = "#js-product-list-top .total-products")
    private WebElement numberOfProducts;

    public String getCategoryTitle() {
        return categoryTitle.getText();
    }

    public boolean areFacetEnabled() {
        if (facets.isEmpty()) {
            logger.info("Facets is disabled.");
            return Boolean.FALSE;
        } else {
            logger.info("Facets is enabled.");
            return Boolean.TRUE;
        }
    }

    public int getNumberOfProducts() {
        String totalProductsText = numberOfProducts.getText().trim().toLowerCase();
        int index = totalProductsText.indexOf("product");
        String number = totalProductsText.substring(index - 3, index).trim();
        logger.info("There are " + number + " products.");
        return Integer.parseInt(number);
    }
}
