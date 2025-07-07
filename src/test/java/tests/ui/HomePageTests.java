package tests.ui;

import components.FooterComponent;
import components.HeaderComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class HomePageTests extends BaseTest {
    HomePage homePage;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    public static final String HOMEPAGE_URL = "https://www.ae.com/us/en";
    private static final String SEARCH_TEXT = "Search";
    private static final String ACCOUNT_TEXT = "Account";
    private static final String FAVORITES_TEXT = "Favorites";
    private static final String BASKET_TEXT = "Shopping Bag";
    private static final String AEO_SUBTITLE_LOGO_TEXT = "American Eagle Outfitters Men's & Women's Clothing, Shoes & Accessories";
    private static final String AEO_LOGO_LINK_TEXT = "Go to aeo homepage";
    private static final String FEATURED_OFFERS_TEXT = "Today's Offers";
    private static final String WOMEN_TAB_TEXT = "Women";
    private static final String MEN_TAB_TEXT = "Men";
    private static final String JEANS_TAB_TEXT = "Jeans";
    private static final String SHOES_AND_ACESS_TAB_TEXT = "Shoes & Accessories";
    private static final String LOUNGEWEAR_TAB_TEXT = "Loungewear & PJs";
    private static final String AERIE_TAB_TEXT = "Aerie";
    private static final String CLEARANCE_TAB_TEXT = "Clearance";
    private static final String COPYRIGHT_TEXT = "© 2025 AEO Management Co. All Rights Reserved";

    String actualTitle = "Men’s & Women’s Jeans, Clothes & Accessories | American Eagle";

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Check HomePage url")
    void getHomePageUrlTest() {
        assertEquals(HOMEPAGE_URL, homePage.getCurrentUrl());
    }

    @Test
    @DisplayName("Check HomePage web title")
    void homePageTitleTest() {
        homePage.chooseCountrySale();

        assertEquals(actualTitle, homePage.getWebTitle());
    }

    @Test
    @DisplayName("Open homepage and check header and main menu titles")
    void openHomePageTest() {
        HeaderComponent header = homePage.getHeader();

        assertAll(
                () -> assertEquals(AEO_LOGO_LINK_TEXT, header.getAeoLogoSubtitleText()),
                () -> assertEquals(AEO_SUBTITLE_LOGO_TEXT, header.getAeoLogoSubtitleValue()),
                () -> assertEquals(FEATURED_OFFERS_TEXT, header.getAeoFeaturedOffersText()),
                () -> assertEquals(WOMEN_TAB_TEXT, header.getAeoWomenTabText()),
                () -> assertEquals(MEN_TAB_TEXT, header.getAeoMenTabText()),
                () -> assertEquals(JEANS_TAB_TEXT, header.getAeoJeansTabText()),
                () -> assertEquals(SHOES_AND_ACESS_TAB_TEXT, header.getAeoShoesAndAccesTabText()),
                () -> assertEquals(LOUNGEWEAR_TAB_TEXT, header.getAeoLoungewearTabText()),
                () -> assertEquals(AERIE_TAB_TEXT, header.getAeoAerieTabText()),
                () -> assertEquals(CLEARANCE_TAB_TEXT, header.getAeoClearanceTabText())
        );
    }

    @Test
    @DisplayName("Check search button on homepage")
    void checkSearchButtonTest() {
        HeaderComponent header = homePage.getHeader();

        assertTrue(header.searchButtonIsDisplayed());
    }

    @Test
    @DisplayName("Click on search button and check search input field is displayed on homepage")
    void checkSearchButtonClickTest() {
        HeaderComponent header = homePage.getHeader();
        header.clickSearchButton();

        assertTrue(header.searchInputIsDisplayed());
        assertEquals(SEARCH_TEXT, header.getSearchTitleText());
    }

    @Test
    @DisplayName("Check account button on homepage")
    void checkAccountButtonTest() {
        HeaderComponent header = homePage.getHeader();

        assertTrue(header.accountButtonIsDisplayed());
    }

    @Test
    @DisplayName("Click on account button and check 'Sign in'")
    void checkSignInButtonTest() {
        HeaderComponent header = homePage.getHeader();
        header.clickAccountButton();

        assertTrue(header.signInButtonIsDisplayed());
        assertEquals(ACCOUNT_TEXT, header.getAccountTitleText());
    }

    @Test
    @DisplayName("Click on account button and check 'Create Account'")
    void checkCreateAccountButtonTest() {
        HeaderComponent header = homePage.getHeader();
        header.clickAccountButton();

        assertTrue(header.CreateAccountButtonIsDisplayed());
        assertEquals(ACCOUNT_TEXT, header.getAccountTitleText());
    }

    @Test
    @DisplayName("Check favorites button on homepage")
    void checkFavoritesButtonTest() {
        HeaderComponent header = homePage.getHeader();

        assertTrue(header.favoriteButtonIsDisplayed());
    }

    @Test
    @DisplayName("Click on favorites button and check 'Favorites' text")
    void checkFavoritesButtonClickTest() {
        HeaderComponent header = homePage.getHeader();
        header.clickFavoriteButton();

        assertEquals(FAVORITES_TEXT, header.getFavoriteTitleText());
    }

    @Test
    @DisplayName("Check basket button on homepage")
    void checkBasketButtonTest() {
        HeaderComponent header = homePage.getHeader();

        assertTrue(header.basketButtonIsDisplayed());
    }

    @Test
    @DisplayName("Click on basket button and check 'Shopping Bag' text")
    void checkBasketButtonClickTest() {
        HeaderComponent header = homePage.getHeader();
        header.clickBasketButton();

        assertEquals(BASKET_TEXT, header.getBasketTitleText());
    }

    @Test
    @DisplayName("Check the page's copyright")
    void checkCopyrightTextTest() {
        FooterComponent footer = homePage.getFooter();
        footer.scrollingToElement();
        assertEquals(COPYRIGHT_TEXT, footer.getCopyrightText());
    }

    @Test
    @DisplayName("Check footer img is displayed")
    void checkFooterImgTest() {
        FooterComponent footer = homePage.getFooter();
        footer.scrollingToElement();
        assertTrue(footer.footerImgIsDisplayed());
    }
}
