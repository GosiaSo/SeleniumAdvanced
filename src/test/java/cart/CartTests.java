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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CartTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CartTests.class);
    private static final User user = new UserFactory().getAlreadyRegisteredUser();

    HeaderPage headerPage = new HeaderPage(driver);
    AccountPage accountPage = new AccountPage(driver);
    LoginPage loginPage = new LoginPage(driver);
    ProductsListPage productsListPage = new ProductsListPage(driver);
    CartPopupPage cartPopupPage = new CartPopupPage(driver);
    ProductPage productPage = new ProductPage(driver);
    ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
    OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(driver);
    OrderDetails orderDetailsFromCart = new OrderDetails();
    OrderDetailsPage orderDetailsPage = new OrderDetailsPage(driver);
    OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);

    @ParameterizedTest
    @Tag("cart")
    @Tag("regression")
    @ValueSource(strings = "THE BEST IS YET POSTER")
    void checkAddProductsToCart(String product) {

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

        double actualTotalCartValue = cartPopupPage.getCostOfAddedItems();
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
    void basketGenericTest() {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Product product = addRandomProductToBasket(products);

            if (Objects.isNull(product)) {
                headerPage.returnToHomePage();
                continue;
            }
            logger.info("Adding item to basket: " + product.getName());
            products.add(product);

            headerPage.returnToHomePage();
        }

        headerPage.goToCartPage();
        double totalPrice = products.stream().mapToDouble(Product::getTotalPrice).sum() + shoppingCartPage.getShippingCost();
        totalPrice = setPrecision(totalPrice);
        Cart expectedCart = new Cart(products, totalPrice);

        headerPage.goToCartPage();

        List<Product> productInShoppingCartPage = shoppingCartPage.getAllProductsFromShoppingCart();
        double totalShoppingCartValue = shoppingCartPage.getTotalCartValue();
        Cart shoppingCart = new Cart(productInShoppingCartPage, totalShoppingCartValue);

        assertThat(shoppingCart).usingRecursiveComparison().isEqualTo(expectedCart);

        for (Product product : products) {
            headerPage.goToCartPage();

            logger.info("<<<< Removing item from basket: " + product.getName() + ">>>>");
            shoppingCartPage.removeFirstProductOnList();
            productInShoppingCartPage.remove(product);

            assertThat(shoppingCart).usingRecursiveComparison().isEqualTo(expectedCart);
        }

        String emptyBasketLabel = shoppingCartPage.getEmptyBasketLabel();
        logger.info("<<<< Checking empty basket label >>>>");
        assertThat(emptyBasketLabel).isEqualTo("There are no more items in your cart");
    }

    @ParameterizedTest
    @ValueSource(strings = "THE BEST IS YET POSTER")
    @Tag("cart")
    @Tag("regression")
    void checkout(String product) {

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

        headerPage.goToSignInPage();
        loginPage.loginAlreadyCreatedUser(user);
        headerPage.returnToHomePage();
    }

    private Product addRandomProductToBasket(List<Product> products) {
        productsListPage.openRandomProduct();

        String name = productPage.getName();

        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                productPage.setRandomQuantity();
                logger.info("Changing quantity of : " + product.getName());
                productPage.addToCartProduct();
                product.setTotalPrice(cartPopupPage.getCostOfAddedItems());
                product.setQuantity(cartPopupPage.getQuantityOfProduct());
                cartPopupPage.closePopupCart();
                return null;
            }
        }
        productPage.setRandomQuantity();
        productPage.addToCartProduct();
        ProductFactory productFactory = new ProductFactory();
        Product product = productFactory.getProductInfo(cartPopupPage);
        cartPopupPage.closePopupCart();

        return product;
    }

    private Cart addProductToCart(String product) {

        productsListPage.openSpecificProduct(product);
        productPage.addToCartProduct();
        Cart cart = getInfoAboutItemAddedToCart();

        cartPopupPage.clickProceedToCheckoutButton();
        shoppingCartPage.proceedToCheckout();

        return cart;
    }

    private Cart getInfoAboutItemAddedToCart() {

        String name = cartPopupPage.getNameOfProduct();
        double price = cartPopupPage.getPriceOfProduct();
        int quantity = cartPopupPage.getQuantityOfProduct();
        double totalItemsValue = cartPopupPage.getCostOfAddedItems();

        Product product = new Product(name, price, quantity, totalItemsValue);
        List<Product> products = new ArrayList<>();
        Cart cart = new Cart(products, totalItemsValue);
        cart.addToList(product);
        return cart;
    }

    private Cart getCartStatusOnConfirmationPage() {
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
        OrderDetails orderDetails = new OrderDetails();

        String date = orderDetailsPage.getDate();
        double totalPrice = orderDetailsPage.getTotalPrice();
        String paymentStatus = orderDetailsPage.getPaymentStatus();
        String deliveryAddress = orderDetailsPage.getDeliveryAddress();
        String invoiceAddress = orderDetailsPage.getInvoiceAddress();

        orderDetails.setDate(date);
        orderDetails.setTotalPrice(totalPrice);
        orderDetails.setPaymentStatus(paymentStatus);
        orderDetails.setDeliveryAddress(deliveryAddress);
        orderDetails.setInvoiceAddress(invoiceAddress);

        return orderDetails;
    }

//    private void removeProductFromCart(List<Product> products, Product product){
//
//        products.remove(product);
//    }

    private double setPrecision(double price) {
        return BigDecimal.valueOf(price)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
