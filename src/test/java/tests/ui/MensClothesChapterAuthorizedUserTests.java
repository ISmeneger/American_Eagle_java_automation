package tests.ui;

import configs.TestPropertiesConfig;
import io.qameta.allure.Severity;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.MensClothesPage;
import pages.ShoppingCartPage;

import static constants.CommonConstants.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.*;

@Tags({@Tag("UI"), @Tag("Extended")})
class MensClothesChapterAuthorizedUserTests extends BaseTest {
    HomePage homePage;
    MensClothesPage mensClothesPage;
    ShoppingCartPage cartPage;
    TestPropertiesConfig config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("positive"), @Tag("defect")})
    @DisplayName("Authorized user add a product to the cart")
    void checkAuthorizedAddItemToCartTest() {
        String expectedQuantityText = "1 Item";

        cartPage = new ShoppingCartPage(driver);
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.selectProductAndItsSizeFromCatalog();

        mensClothesPage.clickToBagButton();
        mensClothesPage.clickViewButton();

        cartPage.signInButtonClick();
        cartPage.inputEmailField(config.getEmail());
        cartPage.inputPasswordField(config.getPassword());
        cartPage.submitButtonClick();

        // Из-за защиты Akamai сайт не позволяет авторизоваться через тест
        assertEquals(BASE_URL + CURRENT_CART_URL, cartPage.getCurrentUrl(),
                "User should remain on the cart page after login");
        assertEquals(CART_TITLE_AUTHORIZED, cartPage.getPageCartHeaderText(),
                "Cart title should reflect authorized user state");
        assertTrue(cartPage.getQuantityOfItemsText().contains(expectedQuantityText),
                "Authorized user should retain cart item after login");
        assertFalse(cartPage.getProductName().isEmpty(),
                "Product name should be displayed in the cart");
    }
}
