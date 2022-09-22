package productandcategories;

import basetest.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.categories.CategoryPage;
import pages.common.HeaderPage;
import pages.homepage.ProductsListPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(CategoriesTest.class);

    @Test
    @Tag("categories")
    @Tag("regression")
    void checkMainCategories() {
        logger.info(">>>> MAIN CATEGORIES <<<<");
        final HeaderPage headerPage = new HeaderPage(driver);
        final CategoryPage categoryPage = new CategoryPage(driver);
        final ProductsListPage productsListPage = new ProductsListPage(driver);

        List<WebElement> mainCategoriesMenuItems = headerPage.getMainCategoriesMenuItems();

        for (int i = 0; i < mainCategoriesMenuItems.size(); i++) {
            WebElement mainCategoriesMenuItem = mainCategoriesMenuItems.get(i);
            retrieveCategoriesTitles(mainCategoriesMenuItem, headerPage, categoryPage);
            areFacetsEnabled(categoryPage);
            checkNumberOfProducts(categoryPage, productsListPage);
        }
    }

    @Test
    @Tag("subcategories")
    @Tag("regression")
    void checkSubCategories() {
        logger.info(">>>> SUBCATEGORIES <<<<");
        final HeaderPage headerPage = new HeaderPage(driver);
        final CategoryPage categoryPage = new CategoryPage(driver);
        final ProductsListPage productsListPage = new ProductsListPage(driver);

        WebElement clothesMainCategory = headerPage.getClothesMainCategory();
        WebElement menSubcategory = headerPage.getMenSubcategory();
        WebElement womenSubcategory = headerPage.getWomenSubcategory();

        WebElement accessoriesMainCategory = headerPage.getAccessoriesMainCategory();
        WebElement homeAccessoriesSubcategory = headerPage.getHomeAccessoriesSubcategory();
        WebElement stationerySubcategory = headerPage.getStationerySubcategory();

        headerPage.clickAdditionalCategory(clothesMainCategory, menSubcategory);
        retrieveSubcategoryTitle(menSubcategory, headerPage, categoryPage);
        areFacetsEnabled(categoryPage);
        logger.info("Expected number of products: " + categoryPage.getNumberOfProducts());
        assertThat(productsListPage.getAllProductTiles().size()).isEqualTo(categoryPage.getNumberOfProducts());

        headerPage.clickAdditionalCategory(clothesMainCategory, womenSubcategory);
        retrieveSubcategoryTitle(womenSubcategory, headerPage, categoryPage);
        areFacetsEnabled(categoryPage);
        logger.info("Expected number of products: " + categoryPage.getNumberOfProducts());
        assertThat(productsListPage.getAllProductTiles().size()).isEqualTo(categoryPage.getNumberOfProducts());

        headerPage.clickAdditionalCategory(accessoriesMainCategory, stationerySubcategory);
        retrieveSubcategoryTitle(stationerySubcategory, headerPage, categoryPage);
        areFacetsEnabled(categoryPage);
        logger.info("Expected number of products: " + categoryPage.getNumberOfProducts());
        assertThat(productsListPage.getAllProductTiles().size()).isEqualTo(categoryPage.getNumberOfProducts());

        headerPage.clickAdditionalCategory(accessoriesMainCategory, homeAccessoriesSubcategory);
        retrieveSubcategoryTitle(homeAccessoriesSubcategory, headerPage, categoryPage);
        areFacetsEnabled(categoryPage);
        logger.info("Expected number of products: " + categoryPage.getNumberOfProducts());
        assertThat(productsListPage.getAllProductTiles().size()).isEqualTo(categoryPage.getNumberOfProducts());
    }

    private void retrieveCategoriesTitles(WebElement mainCategoryMenuItem, HeaderPage headerPage, CategoryPage categoryPage) {
        String nameOfMainMenuItem = headerPage.getNameOfMenuElement(mainCategoryMenuItem);
        headerPage.selectCategory(mainCategoryMenuItem);
        String categoryTitle = categoryPage.getCategoryTitle();
        logger.info("Selected category: " + nameOfMainMenuItem + " | Actual category: " + categoryTitle);
        assertThat(nameOfMainMenuItem).isEqualTo(categoryTitle);
    }

    public void retrieveSubcategoryTitle(WebElement additionalCategory, HeaderPage headerPage, CategoryPage categoryPage) {
        String nameOfSecondLevelMenuItem = headerPage.getNameOfSubCategoryElement(additionalCategory);
        String categoryTitle = categoryPage.getCategoryTitle();
        logger.info("Selected category: " + nameOfSecondLevelMenuItem + " | Actual category: " + categoryTitle);
        assertThat(nameOfSecondLevelMenuItem.toLowerCase()).isEqualTo(categoryTitle.toLowerCase());
    }

    private void checkNumberOfProducts(CategoryPage categoryPage, ProductsListPage productsListPage) {
        int allProductsInSubcategory = productsListPage.getAllProductTiles().size();
        int numberOfProductsSubcategoryOnSite = categoryPage.getNumberOfProducts();
        logger.info("Expected number of products: " + numberOfProductsSubcategoryOnSite);
        assertThat(allProductsInSubcategory).isEqualTo(numberOfProductsSubcategoryOnSite);
    }

    private void areFacetsEnabled(CategoryPage categoryPage) {
        boolean areFacetsEnabled = categoryPage.areFacetEnabled();
        assertThat(areFacetsEnabled).isEqualTo(Boolean.TRUE);
    }
}
