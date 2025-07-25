package tests.ui;

import components.FooterComponent;
import components.HeaderComponent;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.HomePage;
import utils.AllureExtension;

import static constants.CommonConstants.BASE_URL;
import static io.qameta.allure.SeverityLevel.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AllureExtension.class)
@Tags({@Tag("UI"), @Tag("smoke")})
class HomePageTests extends BaseTest {
    HomePage homePage;

    private static final String SEARCH_TEXT = "Search";
    private static final String ACCOUNT_TEXT = "Account";
    private static final String FAVORITES_TEXT = "Favorites";
    private static final String BASKET_TEXT = "Shopping Bag";
    private static final String AEO_SUBTITLE_LOGO_TEXT = "Shop AE";
    private static final String AEO_LOGO_LINK_TEXT = "Go to Shop AE homepage.";
    private static final String FEATURED_OFFERS_TEXT = "Today's Offers";
    private static final String WOMEN_TAB_TEXT = "Women";
    private static final String MEN_TAB_TEXT = "Men";
    private static final String JEANS_TAB_TEXT = "Jeans";
    private static final String SHOES_AND_ACESS_TAB_TEXT = "Shoes & Accessories";
    private static final String LOUNGEWEAR_TAB_TEXT = "Loungewear & PJs";
    private static final String AERIE_TAB_TEXT = "Aerie";
    private static final String CLEARANCE_TAB_TEXT = "Clearance";
    private static final String COPYRIGHT_TEXT = "© 2025 AEO Management Co. All Rights Reserved";

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Verify that the Home Page URL is correct after page load")
    void shouldLoadCorrectHomePageUrlTest() {
        String actualUrl = homePage.getCurrentUrl();

        assertAll("Home Page URL Validation",
                () -> assertNotNull(actualUrl, "Current URL should not be null"),
                () -> assertTrue(actualUrl.startsWith(BASE_URL),
                        "Expected URL to start with: " + BASE_URL + " but was: " + actualUrl),
                () -> assertEquals(BASE_URL, actualUrl,
                        "Home page URL does not match expected base URL")
        );
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Verify that Home Page browser title is correct")
    void homePageTitleTest() {
        String expectedTitle = "Men’s & Women’s Jeans, Clothes & Accessories | American Eagle";

        String actualTitle = homePage.getWebTitle();

        assertAll("Validating Home Page title",
                () -> assertNotNull(actualTitle, "Page title should not be null"),
                () -> assertFalse(actualTitle.isBlank(), "Page title should not be blank"),
                () -> assertEquals(expectedTitle, actualTitle,
                        "Expected title does not match actual")
        );
    }

    @Test
    @Severity(NORMAL)
    @Tag("positive")
    @DisplayName("Verify that all header tab texts are displayed correctly")
    void shouldDisplayCorrectHeaderTabTextsTest() {
        HeaderComponent header = homePage.getHeader();

        assertAll("Logo Texts",
                () -> assertEquals(AEO_LOGO_LINK_TEXT, header.getAeoLogoSubtitleText(),
                        "AEO logo link text mismatch"),
                () -> assertEquals(AEO_SUBTITLE_LOGO_TEXT, header.getAeoLogoSubtitleValue(),
                        "AEO subtitle logo text mismatch")
        );

        assertAll("Featured Offers Section",
                () -> assertEquals(FEATURED_OFFERS_TEXT, header.getAeoFeaturedOffersText(),
                        "Featured Offers text mismatch")
        );

        assertAll("Primary Navigation Tabs",
                () -> assertEquals(WOMEN_TAB_TEXT, header.getAeoWomenTabText(),
                        "Women tab text mismatch"),
                () -> assertEquals(MEN_TAB_TEXT, header.getAeoMenTabText(),
                        "Men tab text mismatch"),
                () -> assertEquals(JEANS_TAB_TEXT, header.getAeoJeansTabText(),
                        "Jeans tab text mismatch"),
                () -> assertEquals(SHOES_AND_ACESS_TAB_TEXT, header.getAeoShoesAndAccesTabText(),
                        "Shoes & Accessories tab text mismatch")
        );

        assertAll("Promo & Branded Tabs",
                () -> assertEquals(LOUNGEWEAR_TAB_TEXT, header.getAeoLoungewearTabText(),
                        "Loungewear tab text mismatch"),
                () -> assertEquals(AERIE_TAB_TEXT, header.getAeoAerieTabText(),
                        "Aerie tab text mismatch"),
                () -> assertEquals(CLEARANCE_TAB_TEXT, header.getAeoClearanceTabText(),
                        "Clearance tab text mismatch")
        );
    }

    @Test
    @Severity(MINOR)
    @Tag("positive")
    @DisplayName("Verify that search button is visible on the homepage header")
    void shouldDisplaySearchButtonOnHomePageTest(){
        HeaderComponent header = homePage.getHeader();
        assertNotNull(header, "Header component should not be null");

        boolean isSearchVisible = header.searchButtonIsDisplayed();

        assertTrue(isSearchVisible, "Search button is expected to be visible but it was not found.");
    }

    @Test
    @Severity(MINOR)
    @Tag("positive")
    @DisplayName("Click on search button and check search input field is displayed on homepage")
    void shouldDisplaySearchInputAfterClickingSearchButtonTest() {
        HeaderComponent header = homePage.getHeader();
        assertNotNull(header, "Header component should not be null");

        header.clickSearchButton();
        boolean isSearchInputVisible = header.searchInputIsDisplayed();
        String actualSearchTitle = header.getSearchTitleText();

        assertAll("Search input field validation",
                () -> assertTrue(isSearchInputVisible,
                        "Search input field should be visible after clicking search button."),
                () -> assertEquals(SEARCH_TEXT, actualSearchTitle,
                        String.format("Expected search input title to be '%s' but was '%s'", SEARCH_TEXT, actualSearchTitle))
        );
    }

    @Test
    @Severity(NORMAL)
    @Tag("positive")
    @DisplayName("Verify account button is visible on the homepage header")
    void shouldDisplayAccountButtonOnHomePageTest() {
        HeaderComponent header = homePage.getHeader();
        assertNotNull(header, "Header component must be initialized");

        boolean isAccountButtonVisible = header.accountButtonIsDisplayed();

        assertTrue(isAccountButtonVisible, "Account button should be visible on the homepage header");
    }

    @Test
    @Severity(NORMAL)
    @Tag("positive")
    @DisplayName("Click on account button and verify 'Sign In' is visible")
    void shouldDisplaySignInButtonWhenAccountButtonClickedTest() {
        HeaderComponent header = homePage.getHeader();
        assertNotNull(header, "HeaderComponent should be initialized");

        header.clickAccountButton();

        assertAll(
                () -> assertTrue(header.signInButtonIsDisplayed(),
                        "'Sign In' button should be visible after clicking account button"),
                () -> assertEquals(ACCOUNT_TEXT, header.getAccountTitleText(),
                        "Account title text should match expected value")
        );
    }

    @Test
    @Severity(NORMAL)
    @Tag("positive")
    @DisplayName("Click on account button and verify 'Create Account' button is visible")
    void shouldDisplayCreateAccountButtonAfterClickingAccountTest() {
        HeaderComponent header = homePage.getHeader();
        assertNotNull(header, "HeaderComponent must be initialized");

        header.clickAccountButton();

        assertAll(
                () -> assertTrue(header.createAccountButtonIsDisplayed(),
                        "'Create Account' button should be visible"),
                () -> assertEquals(ACCOUNT_TEXT, header.getAccountTitleText(),
                        "Account title text should match expected value")
        );
    }

    @Test
    @Severity(MINOR)
    @Tag("positive")
    @DisplayName("Verify 'Favorites' button is visible on the homepage")
    void shouldDisplayFavoritesButtonOnHomePageTest(){
        HeaderComponent header = homePage.getHeader();
        assertNotNull(header, "Header component must be initialized");

        assertTrue(header.favoriteButtonIsDisplayed(),
                "'Favorites' button should be visible in the header");
    }

    @Test
    @Severity(MINOR)
    @Tag("positive")
    @DisplayName("Click 'Favorites' button and verify 'Favorites' title text is displayed")
    void shouldDisplayFavoritesTitleAfterClickingFavoritesButtonTest() {
        HeaderComponent header = homePage.getHeader();
        assertNotNull(header, "Header component must be initialized");

        header.clickFavoriteButton();
        String actualText = header.getFavoriteTitleText();

        assertEquals(FAVORITES_TEXT, actualText, "Expected 'Favorites' title text after clicking the button");
    }

    @Test
    @Severity(MINOR)
    @Tag("positive")
    @DisplayName("Verify that the basket (cart) button is visible on the homepage")
    void shouldDisplayBasketButtonOnHomePageTest() {
        HeaderComponent header = homePage.getHeader();
        assertNotNull(header, "Header component must not be null");

        assertTrue(header.basketButtonIsDisplayed(), "Basket button should be visible on the homepage");
    }

    @Test
    @Severity(MINOR)
    @Tag("positive")
    @DisplayName("Verify clicking the basket button displays the 'Shopping Bag' title")
    void shouldDisplayShoppingBagTitleAfterClickingBasketButtonTest() {
        HeaderComponent header = homePage.getHeader();
        assertNotNull(header, "Header component should not be null");

        header.clickBasketButton();

        String actualTitle = header.getBasketTitleText();
        assertEquals(BASKET_TEXT, actualTitle,
                "Basket title text should match expected 'Shopping Bag'");
    }

    @Test
    @Severity(MINOR)
    @Tag("positive")
    @DisplayName("Verify the footer displays the correct copyright text")
    void shouldDisplayCorrectCopyrightTextTest() {
        FooterComponent footer = homePage.getFooter();
        assertNotNull(footer, "Footer component must not be null");

        footer.scrollingToElement();

        String actualText = footer.getCopyrightText();
        assertEquals(COPYRIGHT_TEXT, actualText,
                "Expected copyright text to match the defined constant.");
    }

    @Test
    @Severity(MINOR)
    @Tag("positive")
    @DisplayName("Verify that the footer image is displayed")
    void shouldDisplayFooterImageTest() {
        FooterComponent footer = homePage.getFooter();
        assertNotNull(footer, "Footer component must not be null");

        footer.scrollingToElementFooterImg();

        assertThat(footer.footerImgIsDisplayed())
                .as("Footer image should be visible on the homepage")
                .isTrue();
    }
}
