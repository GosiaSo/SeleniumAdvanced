package filters;

import basetest.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.categories.FiltersPage;
import pages.common.HeaderPage;
import pages.homepage.ProductTilePage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FiltersTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(FiltersTest.class);

    @Test
    @Tag("filters")
    @Tag("regression")
    void checkPriceFacet() {

        final HeaderPage headerPage = new HeaderPage(driver);
        final FiltersPage filtersPage = new FiltersPage(driver);
        final ProductTilePage productTilePage = new ProductTilePage(driver);

        headerPage.selectCategory("ART");
        filtersPage.slidePriceRightSlider(10);

        List<WebElement> allProductTiles = productTilePage.getProductPrices();
        checkIfItemsPricesMatch(productTilePage, allProductTiles, 9.00, 10.00);
    }

    private void checkIfItemsPricesMatch(ProductTilePage productTilePage, List<WebElement> allProductTiles, double minPrice, double maxPrice) {
        try { //TODO co zrobić z tymy waitami lepiej?
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < allProductTiles.size(); i++) {
            WebElement itemTile = allProductTiles.get(i);
            double actualPrice = productTilePage.checkRangesOfPrices(itemTile);
            logger.info("Expected item price range: $" + minPrice + " - $" + maxPrice);
            assertThat(actualPrice).isBetween(minPrice, maxPrice);
        }
    }
}
