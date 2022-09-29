package pages.cart;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class SingleItemCartPage {

    public SingleItemCartPage(WebElement item) {
        PageFactory.initElements(new DefaultElementLocatorFactory(item), this);
    }

    @FindBy(css = ".product-line-info a")
    private WebElement name;

    @FindBy(css = "span.price")
    private WebElement quantityPrice;

    @FindBy(css = ".js-cart-line-product-quantity")
    private WebElement quantity;

    @FindBy(css = "span.product-price")
    private WebElement totalPrice;


    public String getName() {
        return name.getText().trim();
    }

    public double getQuantityPrice() {
        String priceText = quantityPrice.getText();
        if (priceText.startsWith("$")) {
            String substring = priceText.substring(priceText.indexOf("$") + 1).trim();
            return Double.parseDouble(substring);
        }
        return -1;
    }

    public int getQuantity() {
        String value = quantity.getAttribute("value");
        return Integer.parseInt(value);
    }

    public double getTotalPrice(){
        String text = totalPrice.getText();
        if (text.startsWith("$")) {
            String substring = text.substring(text.indexOf("$") + 1).trim();
            return Double.parseDouble(substring);
        }
        return -1;
    }
}
