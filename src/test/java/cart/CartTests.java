package cart;

import basetest.BaseTest;
import models.Cart;
import models.Product;
import models.ProductFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.cart.BillingAddressPage;
import pages.cart.CartPopupPage;
import pages.cart.ShoppingCartPage;
import pages.common.HeaderPage;
import pages.common.ProductPage;
import pages.common.ProductsListPage;
import pages.signin.LoginPage;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CartTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CartTests.class);

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
    void checkout() {

        HeaderPage headerPage = new HeaderPage(driver);
        ProductsListPage productsListPage = new ProductsListPage(driver);
        CartPopupPage cartPopupPage = new CartPopupPage(driver);
        ProductPage productPage = new ProductPage(driver);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        BillingAddressPage billingAddressPage = new BillingAddressPage(driver);

        headerPage.goToSignInPage();
        loginPage.loginAlreadyCreatedUser();

        headerPage.returnToHomePage();
        productsListPage.openSpecificProduct("THE BEST IS YET POSTER");
        productPage.addToCartProduct();

        cartPopupPage.clickProceedToCheckoutButton();
        shoppingCartPage.proceedToCheckout();

        billingAddressPage.fillBillingForm();
        billingAddressPage.confirmDeliveryOption();
        billingAddressPage.selectPayByCheckOption();
        billingAddressPage.acceptTermsAndConditions();
        billingAddressPage.placeOrder();

    }
}
