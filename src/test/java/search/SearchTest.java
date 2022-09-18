package search;

import BaseTest.BaseTest;
import models.User;
import models.UserFactory;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.common.HeaderPage;
import pages.homepage.ProductTilePage;

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
        assertThat(actualTitle).isEqualTo("TesterSii");
    }

    @Test
    @Tag("search")
    @Tag("regression")
    void checkResultOfSearch() {

        ProductTilePage productTilePage = new ProductTilePage(driver);
        HeaderPage headerPage = new HeaderPage(driver);

        final int initialNumberOfElements = productTilePage.getAllProductTiles().size();

        WebElement randomProductFromList = productTilePage.getRandomProductFromList();
        String nameOfRandomProduct = productTilePage.getNameOfProduct(randomProductFromList);

        headerPage.useSearchEngine(nameOfRandomProduct);
        headerPage.enterSearch();
        if (!productTilePage.checkIfSearchFundItems(initialNumberOfElements)) {
            assertThat(Boolean.TRUE).isEqualTo(Boolean.FALSE);
        }
        String result = productTilePage.getNameOfProduct(productTilePage.retrieveSpecificTile(nameOfRandomProduct));
        assertThat(result).isEqualTo(nameOfRandomProduct);
    }

    @Test
    @Tag("search")
    @Tag("regression")
    void checkDropdownSearch() {
        final String SEARCH_INPUT = "HUMMINGBIRD";
        ProductTilePage productTilePage = new ProductTilePage(driver);
        HeaderPage headerPage = new HeaderPage(driver);

        headerPage.useSearchEngine(SEARCH_INPUT);
        List<String> autocompleteSearchItems = headerPage.retrieveAutocompleteSearchItems(SEARCH_INPUT);

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
