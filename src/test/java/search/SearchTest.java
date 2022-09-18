package search;

import BaseTest.BaseTest;
import models.User;
import models.UserFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.common.SearchEnginePage;
import pages.homepage.ProductTilePage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(SearchTest.class);
    private static final ProductTilePage PRODUCT_TILE_PAGE = new ProductTilePage(driver);
    private static final SearchEnginePage SEARCH_ENGINE_PAGE = new SearchEnginePage(driver);
    private static final String SEARCH_INPUT = "HUMMINGBIRD";

    @Test
        // test for test user factory
    void xxxx() {
        String actualTitle = driver.getTitle();
        UserFactory userFactory = new UserFactory();
        User randomUser = userFactory.getRandomUser();
        logger.debug(randomUser.toString());
        assertThat(actualTitle).isEqualTo("TesterSii");
    }

    @Test
    @Tag("search")
    @Tag("regression")
    void checkResultOfSearch() {
        final int initialNumberOfElements = PRODUCT_TILE_PAGE.getAllProductTiles().size();

        WebElement randomProductFromList = PRODUCT_TILE_PAGE.getRandomProductFromList();
        String nameOfRandomProduct = PRODUCT_TILE_PAGE.getNameOfProduct(randomProductFromList);

        SEARCH_ENGINE_PAGE.useSearchEngine(nameOfRandomProduct);
        SEARCH_ENGINE_PAGE.enterSearch();
        if (!PRODUCT_TILE_PAGE.checkIfSearchFundItems(initialNumberOfElements)) {
            assertThat(Boolean.TRUE).isEqualTo(Boolean.FALSE);
        }
        String result = PRODUCT_TILE_PAGE.getNameOfProduct(PRODUCT_TILE_PAGE.retrieveSpecificTile(nameOfRandomProduct));
        assertThat(result).isEqualTo(nameOfRandomProduct);
    }

    @Test
    @Tag("search")
    @Tag("regression")
    void checkDropdownSearch() {
        SEARCH_ENGINE_PAGE.useSearchEngine(SEARCH_INPUT);
        List<String> autocompleteSearchItems = SEARCH_ENGINE_PAGE.retrieveAutocompleteSearchItems(SEARCH_INPUT);

        boolean flag = Boolean.TRUE;
        for (String listItem : autocompleteSearchItems) {
            if (listItem.toLowerCase().contains(SEARCH_INPUT.toLowerCase())) {
                continue;
            } else {
                flag = Boolean.FALSE;
            }
        }
        assertThat(flag).isEqualTo(Boolean.TRUE);
    }
}
