package pages.cart.orderconfirmation;

import models.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.PageBase;
import pages.cart.SingleItemCartPage;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirmationPage extends PageBase {

    public OrderConfirmationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".order-confirmation-table .order-line")
    private List<WebElement> allRowItems;

    @FindBy(css = "div.order-confirmation-table table td:nth-child(2)")
    private WebElement totalCostValue;

    @FindBy(css = "#order-details li:nth-child(1)")
    private WebElement orderReference;

    @FindBy(css = "#order-details li:nth-child(2)")
    private WebElement paymentMethod;

    @FindBy(css = "#order-details li:nth-child(3)")
    private WebElement shippingMethod;

    public double getTotalCostValue(){
        String text = totalCostValue.getText();
        return getPriceFromText(text);
    }

    public List<SingleItemOrderConfirmation> getAllItems() {
        List<SingleItemOrderConfirmation> allItems = new ArrayList<>();

        for (WebElement item : allRowItems) {
            allItems.add(new SingleItemOrderConfirmation(item));
        }
        return allItems;
    }

    public List<Product> getAllItemsOnConfirmationPage(){
        List<SingleItemOrderConfirmation> allItems = getAllItems();
        List<Product> products = new ArrayList<>();
        for (SingleItemOrderConfirmation singleProduct : allItems) {
            String name = singleProduct.getName();
            double quantityPrice = singleProduct.getQuantityPrice();
            int quantity = singleProduct.getQuantity();
            double totalPrice = singleProduct.getTotalPrice();

            Product product = new Product(name, quantityPrice, quantity, totalPrice);
            products.add(product);
        }
        return products;
    }

    public String getOrderReference(){
        String text = orderReference.getText().trim();
        return text.substring(text.indexOf(":")).trim();
    }

    public String getPaymentMethod(){
        String text = paymentMethod.getText().trim();
        return text.substring(text.indexOf(":") + 1).trim();
    }

    public String getShippingMethod(){
        String text = shippingMethod.getText().trim();
        return text.substring(text.indexOf(":") + 1, text.indexOf("\n")).trim();
    }
}
