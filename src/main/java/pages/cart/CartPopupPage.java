package pages.cart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.PageBase;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartPopupPage extends PageBase {

    public CartPopupPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".cart-content-btn button")
    private WebElement continueShoppingButton;

    @FindBy(css = ".cart-content-btn a")
    private WebElement proceedToCheckoutButton;
    @FindBy(css = ".product-name")
    private WebElement nameOfProduct;

    @FindBy(css = ".modal-content .product-price")
    private WebElement priceOfProduct;

    @FindBy(css = ".modal-content .product-quantity")
    private WebElement quantityOfProduct;

    @FindBy(css = ".modal-content .cart-content p")
    private WebElement quantityOfProductsInCart;

    @FindBy(css = "span.cart-products-count")
    private WebElement productsCountCartIconHeader;

    @FindBy(css = "#blockcart-modal .modal-header button.close")
    private WebElement closeButton;


    public String getNameOfProduct() {
        waitToBeVisible(continueShoppingButton);
        return nameOfProduct.getText();
    }

    public double getPriceOfProduct() {
        String text = priceOfProduct.getText().trim();
        return getPriceFromText(text);
    }

    public int getQuantityOfProduct() {
        String text = quantityOfProduct.getText().trim();
        String substring = text.substring(text.indexOf(":") + 1).trim();
        return Integer.parseInt(substring);
    }

    public int getQuantityOfProductsInCart() {
        String text = quantityOfProductsInCart.getText();
        String substring = text.substring(text.lastIndexOf("items") - 3, text.lastIndexOf("items")).trim();
        return Integer.parseInt(substring);
    }

    public double getCostOfAddedItems() {
        waitToBeVisible(quantityOfProduct);
        double cost = getQuantityOfProduct() * getPriceOfProduct();
        return BigDecimal.valueOf(cost)
                .setScale(4, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public void clickContinueShoppingButton() {
        continueShoppingButton.click();
    }

    public void clickProceedToCheckoutButton() {
        waitToBeClickable(proceedToCheckoutButton);
        proceedToCheckoutButton.click();
    }

    public int getProductsCountFromCartIconHeader() {
        String text = productsCountCartIconHeader.getText().trim();
        String substring = text.substring(text.indexOf("(") + 1, text.indexOf(")"));
        return Integer.parseInt(substring);
    }

    public void closePopupCart() {
        click(closeButton);
    }
}
