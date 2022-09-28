package pages.cart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.PageBase;

public class CartPage extends PageBase {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".cart-content-btn button")
    private WebElement continueShopingButton;
    @FindBy(css = ".product-name")
    private WebElement nameOfProduct;

    @FindBy(css = ".modal-content .product-price")
    private WebElement priceOfProduct;

    @FindBy(css = ".modal-content .product-quantity")
    private WebElement quantityOfProduct;

    @FindBy(css = ".modal-content .cart-content p")
    private WebElement quantityOfProductsInCart;

    @FindBy(xpath = "//span[contains(@class, 'subtotal value')]")
    private WebElement totalProductsValue;

    @FindBy(css = "span.cart-products-count")
    private WebElement productsCountCartIconHeader;


    public String getNameOfProduct() {
        waitToBeVisible(continueShopingButton);
        return nameOfProduct.getText();
    }

    public double getPriceOfProduct() {
        String text = priceOfProduct.getText().trim();
        return getPriceFromText(text);
    }

    public int getQuantityOfProduct() {
        String text = quantityOfProduct.getText().trim();
        String substring = text.substring(text.indexOf(":") + 1).trim();
        int quantity = Integer.parseInt(substring);
        return quantity;
    }

    public int getQuantityOfProductsInCart() {
        String text = quantityOfProductsInCart.getText();
        String substring = text.substring(text.lastIndexOf("items") - 3, text.lastIndexOf("items")).trim();
        int quantity = Integer.parseInt(substring);
        return quantity;
    }

    public double getTotalCartValue() {
        String text = totalProductsValue.getText().trim();
        return getPriceFromText(text);
    }

    public void clickContinueShopingButton() {
        continueShopingButton.click();
    }

    public int getProductsCountFromCartIconHeader() {
        String text = productsCountCartIconHeader.getText().trim();
        String substring = text.substring(text.indexOf("(") + 1, text.indexOf(")"));
        return Integer.parseInt(substring);
    }
}
