package tests.ui;

import configs.TestPropertiesConfig;
import io.qameta.allure.Severity;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.MensClothesPage;
import pages.ShoppingCartPage;

import java.time.Duration;

import static constants.CommonConstants.BASE_URL;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.*;

@Tags({@Tag("UI"), @Tag("Extended")})
class MensClothesChapterTests extends BaseTest {
    HomePage homePage;
    MensClothesPage mensClothesPage;
    ShoppingCartPage cartPage;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    TestPropertiesConfig config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    private static final String CURRENT_MEN_URL = "/c/men/mens?pagetype=clp";
    private static final String CURRENT_CART_URL = "/cart";
    private static final String SUB_TITLE_TEXT = "Men's Clothes";
    private static final String SUCCESSFUL_ADDED_TO_BAG = "Added to bag!";
    private static final String CART_TITLE_UNAUTHORIZED = "Shopping Bag";
    private static final String CART_TITLE_AUTHORIZED = "Ilya's Bag";

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("positive")})
    @DisplayName("Verify Men's Clothes page is opened correctly")
    void checkMensFormPageTest() {
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.movingToElementMen();
        mensClothesPage.selectChapterViewAll();

        assertAll(
                () -> assertEquals(SUB_TITLE_TEXT, mensClothesPage.getMensPageTitle(),
                        "Sub-title should match expected text"),
                () -> assertEquals(BASE_URL + CURRENT_MEN_URL, mensClothesPage.getCurrentUrl(),
                        "Current URL should match expected men's clothing URL")
        );
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("positive")})
    @DisplayName("Add item from catalog to cart and verify success message")
    void addItemFromCatalogToCartTest(){
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.movingToElementMen();
        mensClothesPage.selectChapterViewAll();

        mensClothesPage.closePopUpIfAvailable();

        mensClothesPage.selectFirstAvailableProductAndClickToIt();
        mensClothesPage.selectFirstAvailableSize();
        mensClothesPage.clickToBagButton();

        assertEquals(SUCCESSFUL_ADDED_TO_BAG, mensClothesPage.getSuccessfulAddedToBagText(),
                "Success message text should match expected");
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("positive")})
    @DisplayName("Check if an unauthorized user can add an item to the cart")
    void addItemToBagUnauthorizedUserTest() {
        String expectedQuantityText = "1 Item";
        String expectedItemCost = "$49.45";

        cartPage = new ShoppingCartPage(driver);
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.movingToElementMen();
        mensClothesPage.selectChapterViewAll();
        mensClothesPage.closePopUpIfAvailable();
        mensClothesPage.selectFirstAvailableProductAndClickToIt();
        mensClothesPage.selectFirstAvailableSize();
        mensClothesPage.clickToBagButton();
        mensClothesPage.clickViewButton();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(cartPage.getCurrentUrl())
                .as("User should be redirected to cart page").isEqualTo(BASE_URL + CURRENT_CART_URL);
        softly.assertThat(cartPage.getPageCartHeaderText())
                .as("Page title should match expected title").isEqualTo(CART_TITLE_UNAUTHORIZED);
        softly.assertThat(cartPage.getQuantityOfItemsText())
                .as("Cart should contain 1 item").contains(expectedQuantityText);
        softly.assertThat(cartPage.getProductName())
                .as("Product name should be visible in cart").isNotEmpty();
        softly.assertThat(cartPage.getItemCostInCart())
                .as("Displayed price should match expected price").contains(expectedItemCost);
        softly.assertAll();
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("positive")})
    @DisplayName("Add item to bag and change quantity for unauthorized user")
    void addItemAndChangeQuantityToBagUnauthorizedUserTest() {
        String quantityOfItemsAfterUpdate = "2 Item";
        String shippingCost = "Free";
        String subTotalCost = "$98.90";
        String twoItemCost = "$98.90";

        cartPage = new ShoppingCartPage(driver);
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.movingToElementMen();
        mensClothesPage.selectChapterViewAll();
        mensClothesPage.closePopUpIfAvailable();
        mensClothesPage.selectFirstAvailableProductAndClickToIt();
        mensClothesPage.selectFirstAvailableSize();
        mensClothesPage.clickToBagButton();
        mensClothesPage.clickViewButton();

        cartPage.editItemButton();
        cartPage.movingToElementUpdateBagButton();
        cartPage.addItemToBag();
        cartPage.updateBag();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(cartPage.getQuantityOfItemsText())
                .as("Expected quantity after update").contains(quantityOfItemsAfterUpdate);
        softly.assertThat(cartPage.getItemCostInCart())
                .as("Expected total item cost").contains(twoItemCost);
        softly.assertThat(cartPage.getShippingCostValue())
                .as("Expected shipping cost").contains(shippingCost);
        softly.assertThat(cartPage.getSubTotalCostValue())
                .as("Expected subtotal cost").contains(subTotalCost);
        softly.assertAll();
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("positive")})
    @DisplayName("Remove item from cart as unauthorized user")
    void removeItemFromCartUnauthorizedUserTest() {
        String expectedQuantityText = "1 Item";
        String expectedEmptyCartMessage = "Your bag is empty. Find something you love!";

        cartPage = new ShoppingCartPage(driver);
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.movingToElementMen();
        mensClothesPage.selectChapterViewAll();
        mensClothesPage.closePopUpIfAvailable();
        mensClothesPage.selectFirstAvailableProductAndClickToIt();
        mensClothesPage.closePopUpIfAvailable();
        mensClothesPage.selectFirstAvailableSize();
        mensClothesPage.clickToBagButton();
        mensClothesPage.clickViewButton();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(cartPage.getQuantityOfItemsText())
                .as("Cart should contain 1 item").contains(expectedQuantityText);
        softly.assertThat(cartPage.getProductName())
                .as("Product name should be visible in cart").isNotEmpty();

        cartPage.removeProductInBag();

        softly.assertThat(cartPage.getItemEmptyText())
                .as("Cart should be empty after removing the item").isEqualTo(expectedEmptyCartMessage);
        softly.assertAll();
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("positive")})
    @DisplayName("Verify maximum quantity of items added to cart for unauthorized user")
    void addMaximumQuantityToCartUnauthorizedUserTest() {
        int expectedMaxItemCount = 9;

        mensClothesPage = new MensClothesPage(driver);
        mensClothesPage.movingToElementMen();
        mensClothesPage.selectChapterViewAll();
        mensClothesPage.closePopUpIfAvailable();
        mensClothesPage.selectFirstAvailableProductAndClickToIt();
        mensClothesPage.selectFirstAvailableSize();

        int actualItemCount = mensClothesPage.addItemsUntilButtonDisabled();

        assertEquals(expectedMaxItemCount, actualItemCount,
                "User should not be able to add more than 9 items to the cart");
        assertFalse(mensClothesPage.isAddToCartButtonEnabled(),
                "Add to Cart button should be disabled after reaching max quantity");
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("positive"), @Tag("defect")})
    @DisplayName("Authorized user adds a product to the cart")
    void checkAuthorizedAddItemToCartTest() {
        String expectedQuantityText = "1 Item";

        cartPage = new ShoppingCartPage(driver);
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.movingToElementMen();
        mensClothesPage.selectChapterViewAll();
        mensClothesPage.closePopUpIfAvailable();
        mensClothesPage.selectFirstAvailableProductAndClickToIt();
        mensClothesPage.selectFirstAvailableSize();
        mensClothesPage.clickToBagButton();
        mensClothesPage.clickViewButton();

        cartPage.signInButtonClick();
        cartPage.inputEmailField(config.getEmail());
        cartPage.inputPasswordField(config.getPassword());
        cartPage.submitButtonClick();

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
