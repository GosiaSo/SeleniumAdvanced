package pages.cart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.PageBase;

public class ShoppingCartPage extends PageBase {

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".cart-total")
    private WebElement totalValue;

    public double getTotalCartValue() {
        String text = totalValue.getText().trim();
        return getPriceFromText(text);
    }
}
