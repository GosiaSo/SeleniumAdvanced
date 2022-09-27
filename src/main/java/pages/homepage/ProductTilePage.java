package pages.homepage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

public class ProductTilePage extends PageBase {

    private static final Logger logger = LoggerFactory.getLogger(ProductTilePage.class);

    public ProductTilePage(WebDriver driver) {
        super(driver);
    }

    public double checkPriceOfItem(WebElement element) {
        waitToBeVisible(element);
        String priceText = element.getText();
        if (priceText.startsWith("$")) {
            String substring = priceText.substring(priceText.indexOf("$") + 1);
            double price = Double.parseDouble(substring.trim());
            logger.info("Actual item price: " + price);
            return price;
        }
        return -1;
    }

    public String getNameOfRandomProduct(ProductsListPage productsListPage) {
        WebElement randomProductFromList = productsListPage.getRandomProductFromList();
        return getNameOfProduct(randomProductFromList);
    }
}
