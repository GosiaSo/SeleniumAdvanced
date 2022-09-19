package productandcategories;

import basetest.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.categories.CategoryPage;
import pages.common.HeaderPage;
import pages.homepage.ProductTilePage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CategoriesTest.class);

    @Test
    @Tag("categories")
    @Tag("regression")
    void checkMainCategories() {
        final HeaderPage headerPage = new HeaderPage(driver);
        final CategoryPage categoryPage = new CategoryPage(driver);
        final ProductTilePage productTilePage = new ProductTilePage(driver);

        List<WebElement> mainCategoriesMenuItems = headerPage.getMainCategoriesMenuItems();

        for (int i = 0; i < mainCategoriesMenuItems.size(); i++) {
            WebElement mainCategoriesMenuItem = mainCategoriesMenuItems.get(i);
            retrieveCategoriesTitles(mainCategoriesMenuItem, headerPage, categoryPage);

            areFacetsEnabled(categoryPage);

            int allProductsInCategory = productTilePage.getAllProductTiles().size();
            int numberOfProductsOnSite = categoryPage.getNumberOfProducts();
            logger.info( "" + numberOfProductsOnSite);
            assertThat(allProductsInCategory).isEqualTo(numberOfProductsOnSite);
        }
    }

    private void retrieveCategoriesTitles(WebElement mainCategoryMenuItem, HeaderPage headerPage, CategoryPage categoryPage) {

        String nameOfMainMenuItem = headerPage.getNameOfMenuElement(mainCategoryMenuItem);
        headerPage.selectCategory(mainCategoryMenuItem);
        String categoryTitle = categoryPage.getCategoryTitle();
        logger.info("Selected category: " + nameOfMainMenuItem + " | Actual category: " + categoryTitle);
        assertThat(nameOfMainMenuItem).isEqualTo(categoryTitle);
    }

    private void areFacetsEnabled(CategoryPage categoryPage) {
        boolean areFacetsEnabled = categoryPage.areFacetEnabled();
        assertThat(areFacetsEnabled).isEqualTo(Boolean.TRUE);
    }
}
