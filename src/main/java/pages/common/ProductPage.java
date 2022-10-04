package pages.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.PageBase;

import java.util.Random;

public class ProductPage extends PageBase {

    private static final Logger logger = LoggerFactory.getLogger(ProductPage.class);

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "[data-button-action='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(xpath = "//button[contains(@class, 'touchspin-up')]")
    private WebElement quantityButtonUp;

    @FindBy(css = "div.product-container [itemprop='name']")
    private WebElement name;

    public void addToCartProduct() {
        click(addToCartButton);
    }

    public void setQuantity(int quantity) {
        for (int i = 0; i < quantity - 1; i++) {
            click(quantityButtonUp);
        }
    }

    public void setRandomQuantity() {
        int n = new Random().nextInt(10);
        for (int i = 0; i < n - 1; i++) {
            click(quantityButtonUp);
        }
    }

    public double checkPriceOfItem(WebElement element) {
        waitToBeVisible(element);
        String priceText = element.getText();
        if (priceText.startsWith("$")) {
            double price = getPriceFromText(priceText);
            logger.info("Actual item price: " + price);
            return price;
        }
        return -1;
    }

    public String getNameOfRandomProduct(ProductsListPage productsListPage) {
        WebElement randomProductFromList = productsListPage.getRandomProductFromList();
        return getNameOfProduct(randomProductFromList);
    }

    public String getName(){
        return name.getText().trim();
    }
}
