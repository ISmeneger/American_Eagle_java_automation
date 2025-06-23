package tests.ui;

import components.FooterComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FooterComponentTests extends BaseTest {
    HomePage homePage;
    private static final String TEXT_MUTED = "© 2025 AEO Management Co. All Rights Reserved";

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
    }

    @Test
    @DisplayName("Checking the page's copyright")
    void checkCopyrightText() {
        FooterComponent footer = homePage.getFooter();
        footer.scrollingToElement();
        assertEquals(TEXT_MUTED, homePage.getFooter().getText());
    }
}
