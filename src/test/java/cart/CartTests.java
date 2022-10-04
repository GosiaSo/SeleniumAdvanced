package cart;

import basetest.BaseTest;
import models.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.account.AccountPage;
import pages.account.orderhistory.OrderDetailsPage;
import pages.account.orderhistory.orderhistorytable.OrderHistoryPage;
import pages.account.orderhistory.orderhistorytable.OrderHistoryRow;
import pages.cart.BillingAddressPage;
import pages.cart.CartPopupPage;
import pages.cart.ShoppingCartPage;
import pages.cart.orderconfirmation.OrderConfirmationPage;
import pages.common.HeaderPage;
import pages.common.ProductPage;
import pages.common.ProductsListPage;
import pages.signin.LoginPage;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CartTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CartTests.class);
    private static final User user = new UserFactory().getAlreadyRegisteredUser();

    @ParameterizedTest
    @Tag("cart")
    @Tag("regression")
    @ValueSource(strings = "THE BEST IS YET POSTER")
    void checkAddProductsToCart(String product) {

        HeaderPage headerPage = new HeaderPage(driver);
        ProductsListPage productsListPage = new ProductsListPage(driver);
        CartPopupPage cartPopupPage = new CartPopupPage(driver);
        ProductPage productPage = new ProductPage(driver);

        headerPage.selectCategory("ART");
        productsListPage.openSpecificProduct(product);
        productPage.setQuantity(3);
        productPage.addToCartProduct();

        String actualNameOfProduct = cartPopupPage.getNameOfProduct();
        logger.info("Selected product: " + product + " | Actual product: " + actualNameOfProduct);
        assertThat(actualNameOfProduct).isEqualTo(product);

        double actualPriceOfProduct = cartPopupPage.getPriceOfProduct();
        logger.info("Expected price: 29.0" + " | Actual price: " + actualPriceOfProduct);
        assertThat(actualPriceOfProduct).isEqualTo(29.0);

        int actualQuantityOfProduct = cartPopupPage.getQuantityOfProduct();
        logger.info("Selected product: 3" + " | Actual product: " + actualQuantityOfProduct);
        assertThat(actualQuantityOfProduct).isEqualTo(3);

        int actualQuantityOfProductsInCart = cartPopupPage.getQuantityOfProductsInCart();
        logger.info("Selected product: 3" + " | Actual product: " + actualQuantityOfProductsInCart);
        assertThat(actualQuantityOfProductsInCart).isEqualTo(3);

        double actualTotalCartValue = cartPopupPage.getTotalItemsValue();
        logger.info("Selected product: 87.0" + " | Actual product: " + actualTotalCartValue);
        assertThat(actualTotalCartValue).isEqualTo(87.0);

        cartPopupPage.clickContinueShoppingButton();
        int actualProductsCountFromCartIconHeader = cartPopupPage.getProductsCountFromCartIconHeader();
        logger.info("Expected value of products in cart icon: 3" + " | Actual value of products in cart icon: " + actualProductsCountFromCartIconHeader);
        assertThat(actualProductsCountFromCartIconHeader).isEqualTo(3);
    }

    @Test
    @Tag("cart")
    @Tag("regression")
    void xxxxx() {

        HeaderPage headerPage = new HeaderPage(driver);
        ProductsListPage productsListPage = new ProductsListPage(driver);
        CartPopupPage cartPopupPage = new CartPopupPage(driver);
        ProductPage productPage = new ProductPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);

        List<Product> products = new ArrayList<>();
        double totalPrice = 0;
//TODO ZMIENIĆ Z POWROTEM PĘTLE NA 5!!!!!!!!!!!!!!!!!!!!!!
        for (int i = 0; i < 1; i++) {

            productsListPage.openRandomProduct();
            productPage.setRandomQuantity();
            productPage.addToCartProduct();

            ProductFactory productFactory = new ProductFactory();
            Product product = productFactory.getProductInfo(cartPopupPage);
            totalPrice = totalPrice + product.getTotalPrice();
            products.add(product);

            cartPopupPage.closePopupCart();
            headerPage.returnToHomePage();
        }

        headerPage.goToCartPage();
        totalPrice = totalPrice + shoppingCartPage.getShippingCost();
        Cart actualCart = new Cart(products, totalPrice); // obiekt stworzony z produktów dodawanych do koszyka w trakcie

        // tutaj tworzę nowy obiekt Cart z tej strony shopping cart i porównam
        headerPage.goToCartPage();

        List<Product> productInShoppingCartPage = shoppingCartPage.getAllProductsFromShoppingCart();
        double totalShoppingCartValue = shoppingCartPage.getTotalCartValue();
        Cart shoppingCart = new Cart(productInShoppingCartPage, totalShoppingCartValue);


        // porównanie obiektów całych
        assertThat(actualCart).usingRecursiveComparison().isEqualTo(shoppingCart);
    }

    @ParameterizedTest
    @ValueSource(strings = "THE BEST IS YET POSTER")
    @Tag("cart")
    @Tag("regression")
    void checkout(String product) {
        OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(driver);
        OrderDetails orderDetailsFromCart = new OrderDetails();

        logIn(user);
        Cart cart = addProductToCart(product);

        confirmOrder();
        Cart cartStatusOnConfirmationPage = getCartStatusOnConfirmationPage();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        orderDetailsFromCart.setDate(sdf.format(date));

        logger.info("<<<< Comparising items in cart >>>>");
        assertThat(cart).usingRecursiveComparison().isEqualTo(cartStatusOnConfirmationPage);

        logger.info("<<<< Comparising payment ans shipping method >>>>");
        assertThat(orderConfirmationPage.getPaymentMethod()).isEqualTo("Payments by check");
        assertThat(orderConfirmationPage.getShippingMethod()).isEqualTo("My carrier");

        String orderReferenceFromConfirmationPage = orderConfirmationPage.getOrderReference();
        String orderReferenceFromOrderHistory = checkOrderReferenceNumber(orderReferenceFromConfirmationPage);

        logger.info("<<<< Comparising order reference >>>>");
        assertThat(orderReferenceFromConfirmationPage).isEqualTo(orderReferenceFromOrderHistory);

        orderDetailsFromCart.setTotalPrice(cart.getTotalCost());
        orderDetailsFromCart.setPaymentStatus("Awaiting check payment");
        orderDetailsFromCart.setDeliveryAddress(user.getDeliveryAddress());
        orderDetailsFromCart.setInvoiceAddress(user.getDeliveryAddress());
        logger.info("<<<< Comparising order history details >>>>");
        OrderDetails orderDetailsFromOrderHistoryPage = createOrderDetailsFromOrderHistoryPage();
        assertThat(orderDetailsFromCart).usingRecursiveComparison().isEqualTo(orderDetailsFromOrderHistoryPage);
    }

    private void logIn(User user) {
        HeaderPage headerPage = new HeaderPage(driver);
        LoginPage loginPage = new LoginPage(driver);

        headerPage.goToSignInPage();
        loginPage.loginAlreadyCreatedUser(user);
        headerPage.returnToHomePage();
    }

    private Cart addProductToCart(String product) {
        ProductsListPage productsListPage = new ProductsListPage(driver);
        CartPopupPage cartPopupPage = new CartPopupPage(driver);
        ProductPage productPage = new ProductPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);

        productsListPage.openSpecificProduct(product);
        productPage.addToCartProduct();
        Cart cart = getInfoAboutItemAddedToCart();

        cartPopupPage.clickProceedToCheckoutButton();
        shoppingCartPage.proceedToCheckout();

        return cart;
    }

    private Cart getInfoAboutItemAddedToCart() {
        CartPopupPage cartPopupPage = new CartPopupPage(driver);

        String name = cartPopupPage.getNameOfProduct();
        double price = cartPopupPage.getPriceOfProduct();
        int quantity = cartPopupPage.getQuantityOfProduct();
        double totalItemsValue = cartPopupPage.getTotalItemsValue();

        Product product = new Product(name, price, quantity, totalItemsValue);
        List<Product> products = new ArrayList<>();
        Cart cart = new Cart(products, totalItemsValue);
        cart.addToList(product);
        return cart;
    }

    private Cart getCartStatusOnConfirmationPage() {
        OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(driver);
        List<Product> allItemsOnConfirmationPage = orderConfirmationPage.getAllItemsOnConfirmationPage();
        double totalItemsValue = orderConfirmationPage.getTotalCostValue();

        return new Cart(allItemsOnConfirmationPage, totalItemsValue);
    }

    private void confirmOrder() {
        BillingAddressPage billingAddressPage = new BillingAddressPage(driver);

        billingAddressPage.checkIfInvoiceAddressAlreadyExists();
        billingAddressPage.fillBillingForm(user);
        billingAddressPage.confirmDeliveryOption();
        billingAddressPage.selectPayByCheckOption();
        billingAddressPage.acceptTermsAndConditions();
        billingAddressPage.placeOrder();
    }

    private String checkOrderReferenceNumber(String orderReferenceFromConfirmationPage) {
        HeaderPage headerPage = new HeaderPage(driver);
        AccountPage accountPage = new AccountPage(driver);
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        OrderDetailsPage orderDetailsPage = new OrderDetailsPage(driver);

        headerPage.goToAccountPage();
        accountPage.goToOrderHistoryPage();

        List<OrderHistoryRow> allOrderHistoryRows = orderHistoryPage.getAllOrderHistoryRows();
        Map<Integer, OrderHistoryRow> orders = getOrderHistoryByOrderReference(allOrderHistoryRows, orderReferenceFromConfirmationPage);

        if (orders.isEmpty()) {
            return StringUtils.EMPTY;
        }
        OrderHistoryRow order = orders.entrySet().iterator().next().getValue();
        orderHistoryPage.goToOrderHistoryDetailPage(order);
        return orderDetailsPage.getOrderReference();
    }

    private Map<Integer, OrderHistoryRow> getOrderHistoryByOrderReference(List<OrderHistoryRow> allOrderHistoryRows, String orderReferenceFromConfirmationPage) {
        Map<Integer, OrderHistoryRow> orders = new HashMap<>();
        for (int i = 0; i < allOrderHistoryRows.size(); i++) {
            if (allOrderHistoryRows.get(i).getOrderReference().equalsIgnoreCase(orderReferenceFromConfirmationPage)) {
                orders.put(i, allOrderHistoryRows.get(i));
            }
        }
        logger.info("There is no order in Order History Page with " + orderReferenceFromConfirmationPage + " order reference.");
        return orders;
    }

    private OrderDetails createOrderDetailsFromOrderHistoryPage() {
        OrderDetailsPage orderDetailsPage = new OrderDetailsPage(driver);
        OrderDetails orderDetails = new OrderDetails();

        String date = orderDetailsPage.getDate();
        double totalPrice = orderDetailsPage.getTotalPrice();
        String paymentStatus = orderDetailsPage.getPaymentStatus();
        //TODO te adresy dobrze wypełnić i zobaczyć co on tam beirze
        String deliveryAddress = orderDetailsPage.getDeliveryAddress();
        String invoiceAddress = orderDetailsPage.getInvoiceAddress();

        orderDetails.setDate(date);
        orderDetails.setTotalPrice(totalPrice);
        orderDetails.setPaymentStatus(paymentStatus);
        orderDetails.setDeliveryAddress(deliveryAddress);
        orderDetails.setInvoiceAddress(invoiceAddress);

        return orderDetails;
    }
}
