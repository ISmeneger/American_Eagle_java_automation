package tests.ui;

import components.HeaderComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderComponentTests extends BaseTest {
    HomePage homePage;
    private static final String CURRENT_HOMEPAGE_URL = "https://www.ae.com/us/en";


    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
    }

    @Test
    @DisplayName("Check logo link")
    void checkLogoIcon() {
        HeaderComponent header = homePage.getHeader();
        header.clickSubTitleLink();
        assertEquals(CURRENT_HOMEPAGE_URL, header.getCurrentUrlHomePage());
    }
}
