package tests.ui;

import components.HeaderComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderComponentTests extends BaseTest {
    HomePage homePage;

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

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
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
}


