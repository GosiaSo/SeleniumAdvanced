package pages.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

import java.util.ArrayList;
import java.util.List;

public class ProductsListPage extends PageBase {

    private static final Logger logger = LoggerFactory.getLogger(ProductsListPage.class);

    public ProductsListPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".product-title")
    private List<WebElement> productTileNames;

    @FindBy(css = "div.product-price-and-shipping > span")
    private List<WebElement> productPrices;


    public List<WebElement> getAllProductTiles() {
        return productTileNames;
    }

    public int getNumberOfProducts() {
        return getAllProductTiles().size();
    }

    public WebElement getSpecificProduct(String itemName) {
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

    public void openSpecificProduct(String itemName){
        WebElement product = getSpecificProduct("THE BEST IS YET POSTER");
        click(product);
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

    public List<WebElement> getProductPrices() {
        return productPrices;
    }

    public boolean checkIfItemsPricesAreBetween(ProductTilePage productTilePage, double minPrice, double maxPrice) {
        List<WebElement> allProductTiles = getProductPrices();

        List<Boolean> flag = new ArrayList<>();
        for (int i = 0; i < allProductTiles.size(); i++) {
            WebElement itemTile = allProductTiles.get(i);
            double actualPrice = productTilePage.checkPriceOfItem(itemTile);
            logger.info("Expected item price range: $" + minPrice + " - $" + maxPrice);
            if (actualPrice >= minPrice && actualPrice <= maxPrice) {
                flag.add(Boolean.TRUE);
            }
        }
        return !flag.contains(Boolean.FALSE);
    }
}
