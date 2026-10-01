package tests.ui;

import configs.TestPropertiesConfig;
import io.qameta.allure.Severity;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;
import pages.AccountPage;
import pages.HomePage;
import pages.ProductPage;
import pages.ShoppingCartPage;
import steps.ProductCatalogSteps;

import static constants.CommonConstants.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.*;

@Tags({
        @Tag("UI"),
        @Tag("extended")
})
class MensClothesChapterAuthorizedUserTests extends BaseTest {

    HomePage homePage;
    ProductPage productPage;
    ShoppingCartPage cartPage;
    AccountPage accountPage;
    ProductCatalogSteps productCatalogSteps;

    TestPropertiesConfig config =
            ConfigFactory.create(
                    TestPropertiesConfig.class,
                    System.getProperties()
            );

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        homePage.closeBlockingOverlaysIfAvailable();
    }

    @Test
    @Disabled(
            "Automated sign-in is blocked by the site's anti-bot protection. " +
                    "Manual sign-in works successfully."
    )
    @Severity(CRITICAL)
    @Tags({
            @Tag("positive"),
            @Tag("defect")
    })
    @DisplayName("Authorized user add a product to the cart")
    void checkAuthorizedAddItemToCartTest() {

        String expectedQuantityText = "1 Item";

        productCatalogSteps = new ProductCatalogSteps(driver);
        productPage = new ProductPage(driver);
        cartPage = new ShoppingCartPage(driver);
        accountPage = new AccountPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfPresent();
        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();
        productPage.openShoppingBag();

        cartPage.signInButtonClick();

        accountPage.inputEmailField(config.getEmail());
        accountPage.continueButtonClick();
        accountPage.selectPasswordSignInMethod();
        accountPage.inputPasswordField(config.getPassword());
        accountPage.submitSignInButtonClick();

        assertEquals(
                BASE_URL + CURRENT_CART_URL,
                cartPage.getCurrentUrl(),
                "User should return to the cart page after login"
        );

        assertEquals(
                CART_TITLE_AUTHORIZED,
                cartPage.getPageCartHeaderText(),
                "Cart title should reflect authorized user state"
        );

        assertTrue(
                cartPage.getQuantityOfItemsText()
                        .contains(expectedQuantityText),
                "Authorized user should retain cart item after login"
        );

        assertFalse(
                cartPage.getProductName().isEmpty(),
                "Product name should be displayed in the cart"
        );
    }
}