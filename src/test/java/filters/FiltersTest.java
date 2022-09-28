package filters;

import basetest.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.categories.FiltersPage;
import pages.common.HeaderPage;
import pages.common.ProductTilePage;
import pages.common.ProductsListPage;

import static org.assertj.core.api.Assertions.assertThat;

public class FiltersTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(FiltersTest.class);

    @Test
    @Tag("filters")
    @Tag("regression")
    void checkPriceFacet() {

        HeaderPage headerPage = new HeaderPage(driver);
        ProductsListPage productsListPage = new ProductsListPage(driver);
        FiltersPage filtersPage = new FiltersPage(driver);
        ProductTilePage productTilePage = new ProductTilePage(driver);

        headerPage.selectCategory("ART");
        int initialNumberOfProducts = productsListPage.getNumberOfProducts();
        filtersPage.slidePriceRightSlider(10);

        boolean ifItemsPricesAreBetween = productsListPage.checkIfItemsPricesAreBetween(productTilePage, 9.00, 10.00);
        assertThat(Boolean.TRUE).isEqualTo(ifItemsPricesAreBetween);

        int numberOfFilteredProducts = productsListPage.getNumberOfProducts();
        filtersPage.clearFilters();
        int finalNumberOfProducts = productsListPage.getNumberOfProducts();
        logger.info("Initial number of products: " + initialNumberOfProducts +" | Number of filtered products: " + numberOfFilteredProducts + " | Final number of products after clear filters: " + finalNumberOfProducts);
        assertThat(initialNumberOfProducts).isEqualTo(finalNumberOfProducts);
    }
}
