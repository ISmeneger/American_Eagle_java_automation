package tests.ui.search;

import components.HeaderComponent;
import io.qameta.allure.Severity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.SearchResultsPage;
import tests.ui.BaseTest;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Tags({
        @Tag("UI"),
        @Tag("search")
})
class SearchTests extends BaseTest {

    HomePage homePage;
    HeaderComponent headerComponent;
    SearchResultsPage searchResultsPage;

    @BeforeEach
    void setupPage() {

        homePage = new HomePage(driver);
        headerComponent = new HeaderComponent(driver);
        searchResultsPage = new SearchResultsPage(driver);

        homePage.closeBlockingOverlaysIfAvailable();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Search for existing products")
    void searchExistingProductsTest() {

        String searchQuery = "jeans";

        headerComponent.clickSearchButton();
        headerComponent.enterSearchQuery(searchQuery);
        headerComponent.selectSearchSuggestion(searchQuery);

        String searchResultsMessage =
                searchResultsPage.getSearchResultsMessage();

        int displayedProductsCount =
                searchResultsPage.getDisplayedProductsCount();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(searchResultsMessage)
                .as("Search results message should contain search query")
                .containsIgnoringCase(searchQuery);

        softly.assertThat(displayedProductsCount)
                .as("Search should return at least one product")
                .isGreaterThan(0);

        softly.assertAll();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("negative")
    @DisplayName("Search for non-existing product")
    void searchNonExistingProductTest() {

        String searchQuery = "ывапыва";

        headerComponent.clickSearchButton();
        headerComponent.enterSearchQuery(searchQuery);
        headerComponent.submitSearchQuery();

        String noResultsMessage =
                searchResultsPage.getNoSearchResultsMessage();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(noResultsMessage)
                .as("No results message should be displayed")
                .containsIgnoringCase(
                        "Sorry! We couldn't find a match for"
                );

        softly.assertThat(noResultsMessage)
                .as("No results message should contain the search query")
                .contains(searchQuery);

        softly.assertAll();
    }
}