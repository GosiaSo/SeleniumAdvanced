package cart;

import basetest.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.cart.CartPage;
import pages.common.HeaderPage;
import pages.common.ProductTilePage;
import pages.common.ProductsListPage;

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
        CartPage cartPage = new CartPage(driver);
        ProductTilePage productTilePage = new ProductTilePage(driver);

        headerPage.selectCategory("ART");
        productsListPage.openSpecificProduct(product);
        productTilePage.setQuantity(3);
        productTilePage.addToCartProduct();

        String actualNameOfProduct = cartPage.getNameOfProduct();
        logger.info("Selected product: " + product + " | Actual product: " + actualNameOfProduct);
        assertThat(actualNameOfProduct).isEqualTo(product);

        double actualPriceOfProduct = cartPage.getPriceOfProduct();
        logger.info("Expected price: 29.0" + " | Actual price: " + actualPriceOfProduct);
        assertThat(actualPriceOfProduct).isEqualTo(29.0);

        int actualQuantityOfProduct = cartPage.getQuantityOfProduct();
        logger.info("Selected product: 3" + " | Actual product: " + actualQuantityOfProduct);
        assertThat(actualQuantityOfProduct).isEqualTo(3);

        int actualQuantityOfProductsInCart = cartPage.getQuantityOfProductsInCart();
        logger.info("Selected product: 3" + " | Actual product: " + actualQuantityOfProductsInCart);
        assertThat(actualQuantityOfProductsInCart).isEqualTo(3);

        double actualTotalCartValue = cartPage.getTotalCartValue();
        logger.info("Selected product: 87.0" + " | Actual product: " + actualTotalCartValue);
        assertThat(actualTotalCartValue).isEqualTo(87.0);

        cartPage.clickContinueShopingButton();
        int actualProductsCountFromCartIconHeader = cartPage.getProductsCountFromCartIconHeader();
        logger.info("Expected value of products in cart icon: 3" + " | Actual value of products in cart icon: " + actualProductsCountFromCartIconHeader);
        assertThat(actualProductsCountFromCartIconHeader).isEqualTo(3);
    }
}
