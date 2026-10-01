package tests.ui;

import components.FooterComponent;
import components.HeaderComponent;
import io.qameta.allure.Severity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import pages.HomePage;

import static constants.CommonConstants.BASE_URL;
import static io.qameta.allure.SeverityLevel.*;
import static org.junit.jupiter.api.Assertions.*;

@Tags({
        @Tag("UI"),
        @Tag("smoke")
})
class HomePageTests extends BaseTest {
    HomePage homePage;

    private static final String ACCOUNT_TEXT = "Account";
    private static final String FAVORITES_TEXT = "Favorites";
    private static final String BASKET_TEXT = "Shopping Bag";
    private static final String AEO_SUBTITLE_LOGO_TEXT = "Shop AE";
    private static final String AEO_LOGO_LINK_TEXT = "Go to Shop AE homepage.";
    private static final String FEATURED_OFFERS_TEXT = "Today's Offers";
    private static final String NEW_TAB_TEXT = "New";
    private static final String WOMEN_TAB_TEXT = "Women";
    private static final String MEN_TAB_TEXT = "Men";
    private static final String JEANS_TAB_TEXT = "Jeans";
    private static final String AERIE_TAB_TEXT = "Aerie";
    private static final String CLEARANCE_TAB_TEXT = "Clearance";
    private static final String COPYRIGHT_TEXT = "© 2026 AEO Management Co. All Rights Reserved";

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        homePage.closeBlockingOverlaysIfAvailable();
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
                () -> assertEquals(NEW_TAB_TEXT, header.getAeoNewTabText(),
                        "New tab text mismatch"),
                () -> assertEquals(WOMEN_TAB_TEXT, header.getAeoWomenTabText(),
                        "Women tab text mismatch"),
                () -> assertEquals(MEN_TAB_TEXT, header.getAeoMenTabText(),
                        "Men tab text mismatch"),
                () -> assertEquals(JEANS_TAB_TEXT, header.getAeoJeansTabText(),
                        "Jeans tab text mismatch"),
                () -> assertEquals(AERIE_TAB_TEXT, header.getAeoAerieTabText(),
                        "Aerie tab text mismatch"),
                () -> assertEquals(CLEARANCE_TAB_TEXT, header.getAeoClearanceTabText(),
                        "Clearance tab text mismatch")
        );
    }

    @Test
    @Severity(NORMAL)
    @Tag("positive")
    @DisplayName("Verify main header controls are displayed")
    void shouldDisplayMainHeaderControlsTest() {

        HeaderComponent header = homePage.getHeader();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(header)
                .as("Header component should be initialized")
                .isNotNull();

        softly.assertThat(header.searchButtonIsDisplayed())
                .as("Search button should be visible")
                .isTrue();

        softly.assertThat(header.accountButtonIsDisplayed())
                .as("Account button should be visible")
                .isTrue();

        softly.assertThat(header.favoriteButtonIsDisplayed())
                .as("Favorites button should be visible")
                .isTrue();

        softly.assertThat(header.basketButtonIsDisplayed())
                .as("Basket button should be visible")
                .isTrue();

        softly.assertAll();
    }

    @Test
    @Severity(MINOR)
    @Tag("positive")
    @DisplayName("Click on search button and check search input field is displayed on homepage")
    void shouldDisplaySearchInputAfterClickingSearchButtonTest() {
        HeaderComponent header = homePage.getHeader();
        assertNotNull(header, "Header component should not be null");

        header.clickSearchButton();

        assertTrue(
                header.searchInputIsDisplayed(),
                "Search input field should be visible after clicking search button."
        );
    }

    @Test
    @Severity(NORMAL)
    @Tag("positive")
    @DisplayName("Verify account panel content after clicking Account button")
    void shouldDisplayAccountPanelContentTest() {

        HeaderComponent header = homePage.getHeader();

        header.clickAccountButton();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(header.getAccountTitleText())
                .as("Account panel title should match expected value")
                .isEqualTo(ACCOUNT_TEXT);

        softly.assertThat(header.signInButtonIsDisplayed())
                .as("'Sign In' button should be visible")
                .isTrue();

        softly.assertThat(header.createAccountButtonIsDisplayed())
                .as("'Create Account' button should be visible")
                .isTrue();

        softly.assertAll();
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
    @DisplayName("Verify footer content is displayed correctly")
    void shouldDisplayCorrectFooterContentTest() {

        FooterComponent footer = homePage.getFooter();

        footer.scrollingToElement();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(footer.getCopyrightText())
                .as("Copyright text should match expected value")
                .isEqualTo(COPYRIGHT_TEXT);

        footer.scrollingToElementFooterImg();

        softly.assertThat(footer.footerImgIsDisplayed())
                .as("Footer image should be visible")
                .isTrue();

        softly.assertAll();
    }
}