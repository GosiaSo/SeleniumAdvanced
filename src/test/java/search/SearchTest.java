package search;

import basetest.BaseTest;
import models.User;
import models.UserFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.common.SearchEnginePage;
import pages.homepage.ProductTilePage;
import pages.homepage.ProductsListPage;

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

        final int initialNumberOfElements = productsListPage.getAllProductTiles().size();

        WebElement randomProductFromList = productsListPage.getRandomProductFromList();
        String nameOfRandomProduct = productTilePage.getNameOfProduct(randomProductFromList);

        searchEnginePage.useSearchEngine(nameOfRandomProduct);
        searchEnginePage.enterSearch();
        if (!productsListPage.checkIfSearchFundItems(initialNumberOfElements)) {
            assertThat(Boolean.TRUE).isEqualTo(Boolean.FALSE);
        }
        String result = productTilePage.getNameOfProduct(productsListPage.retrieveSpecificTile(nameOfRandomProduct));
        assertThat(result).isEqualTo(nameOfRandomProduct);
    }

    @Test
    @Tag("search")
    @Tag("regression")
    void checkDropdownSearch() {
        final String SEARCH_INPUT = "HUMMINGBIRD";
        ProductsListPage productsListPage = new ProductsListPage(driver);
        SearchEnginePage searchEnginePage = new SearchEnginePage(driver);

        searchEnginePage.useSearchEngine(SEARCH_INPUT);
        List<String> autocompleteSearchItems = searchEnginePage.retrieveAutocompleteSearchItems(SEARCH_INPUT);

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
