package tests.ui;

import configs.ConfigProvider;
import configs.TestPropertiesConfig;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.ProductPage;
import pages.ShoppingCartPage;
import steps.ProductCatalogSteps;

import static constants.CommonConstants.AUTHORIZED_CART_TITLE;
import static constants.CommonConstants.CART_PATH;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tags({
        @Tag("UI"),
        @Tag("extended")
})
class MensClothesChapterAuthorizedUserTests extends BaseTest {

    private static final TestPropertiesConfig CONFIG =
            ConfigProvider.get();

    private static final String UI_BASE_URL =
            CONFIG.getUiBaseUrl();

    @BeforeEach
    void setupPage() {
        HomePage homePage = new HomePage(driver);
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
    @DisplayName("Authorized user adds a product to the cart")
    void authorizedUserAddsProductToCartTest() {

        String expectedQuantityText = "1 Item";

        ProductCatalogSteps productCatalogSteps =
                new ProductCatalogSteps(driver);

        ProductPage productPage =
                new ProductPage(driver);

        ShoppingCartPage cartPage =
                new ShoppingCartPage(driver);

        AccountPage accountPage =
                new AccountPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfAvailable();
        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();
        productPage.openShoppingBag();

        cartPage.clickSignInButton();

        accountPage.inputEmailField(CONFIG.getEmail());
        accountPage.clickContinueButton();
        accountPage.selectPasswordSignInMethod();
        accountPage.inputPasswordField(CONFIG.getPassword());
        accountPage.clickSubmitSignInButton();

        assertEquals(
                UI_BASE_URL + CART_PATH,
                cartPage.getCurrentUrl(),
                "User should return to the cart page after login"
        );

        assertEquals(
                AUTHORIZED_CART_TITLE,
                cartPage.getCartPageHeader(),
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