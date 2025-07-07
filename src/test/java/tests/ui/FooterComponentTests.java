package tests.ui;

import components.FooterComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FooterComponentTests extends BaseTest {
    HomePage homePage;
    private static final String COPYRIGHT_TEXT = "© 2025 AEO Management Co. All Rights Reserved";

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
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
