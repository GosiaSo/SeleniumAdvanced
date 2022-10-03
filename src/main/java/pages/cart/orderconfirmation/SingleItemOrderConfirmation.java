package pages.cart.orderconfirmation;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class SingleItemOrderConfirmation {

    public SingleItemOrderConfirmation(WebElement item) {
        PageFactory.initElements(new DefaultElementLocatorFactory(item), this);
    }

    @FindBy(css = "div.col-sm-4.col-xs-9.details span")
    private WebElement name;

    @FindBy(css = ".col-sm-6.col-xs-12.qty .row div:nth-child(1)")
    private WebElement quantityPrice;

    @FindBy(css = ".col-sm-6.col-xs-12.qty .row div:nth-child(2)")
    private WebElement quantity;

    @FindBy(css = ".col-sm-6.col-xs-12.qty .row div:nth-child(3)")
    private WebElement totalPrice;

    public String getName() {
        String text = name.getText().trim();
        return text.substring(0, text.indexOf(" -")).trim();
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
        return Integer.parseInt(quantity.getText());
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
