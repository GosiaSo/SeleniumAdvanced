package pages.homepage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

import java.util.List;

public class ProductsListPage extends PageBase {

    private static final Logger logger = LoggerFactory.getLogger(ProductsListPage.class);

    public ProductsListPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".product-title")
    private List<WebElement> productTileNames;


    public List<WebElement> getAllProductTiles() {
        return productTileNames;
    }

    public WebElement retrieveSpecificTile(String itemName) {
        for (WebElement productTile : productTileNames) {
            String productName = getNameOfProduct(productTile);
            if (productName.equalsIgnoreCase(itemName)) {
                logger.info("Found specific item: " + productTile.getAttribute("textContent"));
                return productTile;
            }
        }
        logger.info("There is no such product item named: " + itemName);
        return null;
    }

    public boolean checkIfSearchFundItems(int initialNumberOfElements) {
        int size = getAllProductTiles().size();
        if (initialNumberOfElements > size) {
            logger.debug("Search engine found " + size + " items.");
            return Boolean.TRUE;
        } else {
            logger.warn("Search engine found no items.");
        }
        return Boolean.FALSE;
    }

    public WebElement getRandomProductFromList() {
        return getRandomElement(productTileNames);
    }
}
