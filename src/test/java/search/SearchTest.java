package search;

import basetest.BaseTest;
import models.User;
import models.UserFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.common.SearchEnginePage;
import pages.common.ProductTilePage;
import pages.common.ProductsListPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(SearchTest.class);

    @Test
        // test for test user factory
    void xxxx() {
        String actualTitle = driver.getTitle();
        UserFactory userFactory = new UserFactory();
        User randomUser = userFactory.getRandomUser();
        logger.debug(randomUser.toString());
        assertThat(actualTitle).isEqualTo("Search");
    }

    @Test
    @Tag("search")
    @Tag("regression")
    void checkResultOfSearch() {

        ProductsListPage productsListPage = new ProductsListPage(driver);
        SearchEnginePage searchEnginePage = new SearchEnginePage(driver);
        ProductTilePage productTilePage = new ProductTilePage(driver);

        int initialNumberOfElements = productsListPage.getNumberOfProducts();

        String nameOfRandomProduct = productTilePage.getNameOfRandomProduct(productsListPage);

        searchEnginePage.useSearchEngine(nameOfRandomProduct);
        searchEnginePage.enterSearch();
        if (!productsListPage.checkIfSearchFundItems(initialNumberOfElements)) {
            assertThat(Boolean.TRUE).isEqualTo(Boolean.FALSE);
        }
        String result = productTilePage.getNameOfProduct(productsListPage.getSpecificTile(nameOfRandomProduct));
        assertThat(result).isEqualTo(nameOfRandomProduct);
    }

    @Test
    @Tag("search")
    @Tag("regression")
    void checkDropdownSearch() {
        final String SEARCH_INPUT = "HUMMINGBIRD";
        SearchEnginePage searchEnginePage = new SearchEnginePage(driver);

        searchEnginePage.useSearchEngine(SEARCH_INPUT);
        List<String> autocompleteSearchItems = searchEnginePage.getAutocompleteSearchItems(SEARCH_INPUT);

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
