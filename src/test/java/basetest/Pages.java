package basetest;

import models.OrderDetails;
import org.junit.jupiter.api.BeforeEach;
import pages.account.AccountPage;
import pages.account.orderhistory.OrderDetailsPage;
import pages.account.orderhistory.orderhistorytable.OrderHistoryPage;
import pages.cart.CartPopupPage;
import pages.cart.ShoppingCartPage;
import pages.cart.orderconfirmation.OrderConfirmationPage;
import pages.common.HeaderPage;
import pages.common.ProductPage;
import pages.common.ProductsListPage;
import pages.signin.LoginPage;

public class Pages extends BaseTest {

    public HeaderPage headerPage;
    public AccountPage accountPage;
    public LoginPage loginPage;
    public ProductsListPage productsListPage;
    public CartPopupPage cartPopupPage;
    public ProductPage productPage;
    public ShoppingCartPage shoppingCartPage;
    public OrderConfirmationPage orderConfirmationPage;
    public OrderDetails orderDetailsFromCart;
    public OrderDetailsPage orderDetailsPage;
    public OrderHistoryPage orderHistoryPage;

    @BeforeEach
    public void setup() {
        headerPage = new HeaderPage(driver);
        accountPage = new AccountPage(driver);
        loginPage = new LoginPage(driver);
        productsListPage = new ProductsListPage(driver);
        cartPopupPage = new CartPopupPage(driver);
        productPage = new ProductPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
        orderConfirmationPage = new OrderConfirmationPage(driver);
        orderDetailsFromCart = new OrderDetails();
        orderDetailsPage = new OrderDetailsPage(driver);
        orderHistoryPage = new OrderHistoryPage(driver);
    }
}
