package pages.cart;

import models.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.PageBase;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartPage extends PageBase {
    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".cart-total")
    private WebElement totalValue;

    @FindBy(css = ".cart-item")
    private List<WebElement> allItemsOnList;

    @FindBy(css = "#cart-subtotal-shipping span:nth-child(2)")
    private WebElement shippingCost;

    @FindBy(css = ".checkout a")
    private WebElement proceedToCheckoutButton;

    @FindBy(css = "a.remove-from-cart")
    private WebElement removeItemButton;

    @FindBy(css = "span.no-items")
    private WebElement emptyBasketLabel;

    public double getTotalCartValue() {
        String text = totalValue.getText().trim();
        return getPriceFromText(text);
    }

    public double getShippingCost() {
        String text = shippingCost.getText().trim();
        return getPriceFromText(text);
    }

    public void proceedToCheckout() {
        click(proceedToCheckoutButton);
    }

    public List<SingleItemCartPage> getAllItemsOnCartList() {
        List<SingleItemCartPage> allItems = new ArrayList<>();

        for (WebElement item : allItemsOnList) {
            allItems.add(new SingleItemCartPage(item));
        }
        return allItems;
    }

    public List<Product> getAllProductsFromShoppingCart() {
        List<SingleItemCartPage> allItemsOnCartList = getAllItemsOnCartList();
        List<Product> products = new ArrayList<>();
        for (SingleItemCartPage singleItemCartPage : allItemsOnCartList) {
            String name = singleItemCartPage.getName();
            double quantityPrice = singleItemCartPage.getQuantityPrice();
            int quantity = singleItemCartPage.getQuantity();
            double totalPrice = singleItemCartPage.getTotalPrice();

            Product product = new Product(name, quantityPrice, quantity, totalPrice);
            products.add(product);
        }
        return products;
    }

    public void removeFirstProductOnList() {
        click(removeItemButton);
    }

    public String getEmptyBasketLabel() {
        waitToBeVisible(emptyBasketLabel);
        return emptyBasketLabel.getText().trim();
    }
}
