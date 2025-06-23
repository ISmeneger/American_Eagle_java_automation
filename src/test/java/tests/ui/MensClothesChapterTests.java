package tests.ui;

import components.HeaderComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.MensClothesPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MensClothesChapterTests extends BaseTest {
    HomePage homePage;
    MensClothesPage mensClothesPage;

    private static final String SUB_TITLE_TEXT = "Men's Clothes";

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Check Mens clothes page")
    void checkLogoIcon() throws InterruptedException {
        mensClothesPage = new MensClothesPage(driver);
        mensClothesPage.movingToElementMen();
        mensClothesPage.chooseChapterViewAll();

        Thread.sleep(6000);

//        assertEquals(SUB_TITLE_TEXT, mensClothesPage.getSubPageTitle());
    }

}
