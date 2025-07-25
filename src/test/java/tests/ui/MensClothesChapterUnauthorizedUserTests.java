package tests.ui;

import io.qameta.allure.Severity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.HomePage;
import pages.MensClothesPage;
import pages.ShoppingCartPage;
import utils.AllureExtension;

import static constants.CommonConstants.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AllureExtension.class)
@Tags({@Tag("UI"), @Tag("extended")})
class MensClothesChapterUnauthorizedUserTests extends BaseTest {
    HomePage homePage;
    MensClothesPage mensClothesPage;
    ShoppingCartPage cartPage;

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
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
    @Tag("positive")
    @DisplayName("Add item from catalog to cart and verify success message is shown")
    void addItemFromCatalogToCartTest(){
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.selectProductAndItsSizeFromCatalog();
        mensClothesPage.clickToBagButton();

        String actualMessage = mensClothesPage.getSuccessfulAddedToBagText();
        assertEquals(SUCCESSFUL_ADDED_TO_BAG, actualMessage,
                "Success message text should match expected");
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Check that product price in catalog matches the price in cart")
    void priceMatchesBetweenCatalogAndCartTest() {
        String expectedQuantityText = "1 Item";

        cartPage = new ShoppingCartPage(driver);
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.selectFirstAvailableProductFromCatalog();

        String catalogPriceRaw = mensClothesPage.getCatalogPrice();
        String catalogPrice = catalogPriceRaw.replace("Now", "").trim();

        mensClothesPage.selectFirstAvailableSize();
        mensClothesPage.clickToBagButton();
        mensClothesPage.clickViewButton();

        String cartPrice = mensClothesPage.getCartPrice();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(cartPage.getCurrentUrl())
                .as("User should be on cart page")
                .isEqualTo(BASE_URL + CURRENT_CART_URL);

        softly.assertThat(cartPage.getPageCartHeaderText())
                .as("Cart page title should match expected")
                .isEqualTo(CART_TITLE_UNAUTHORIZED);

        softly.assertThat(cartPage.getQuantityOfItemsText())
                .as("Cart should contain 1 item")
                .contains(expectedQuantityText);

        softly.assertThat(cartPage.getProductName())
                .as("Product name in cart should not be empty")
                .isNotEmpty();

        softly.assertThat(catalogPrice)
                .as("Product price in cart should match catalog price")
                .isEqualTo(cartPrice);

        softly.assertAll();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Add item to bag and change quantity")
    void addItemAndChangeQuantityInBagTest() {
        String quantityOfItemsBeforeUpdate = "1 Item";
        String quantityOfItemsAfterUpdate = "2 Item";

        cartPage = new ShoppingCartPage(driver);
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.selectProductAndItsSizeFromCatalog();
        mensClothesPage.clickToBagButton();
        mensClothesPage.clickViewButton();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(cartPage.getQuantityOfItemsText())
                .as("Initial quantity should be 1")
                .contains(quantityOfItemsBeforeUpdate);

        cartPage.editItemButton();
        cartPage.movingToElementUpdateBagButton();
        cartPage.addItemToBag();
        cartPage.updateBag();

        softly.assertThat(cartPage.getQuantityOfItemsText())
                .as("Quantity after update should be 2")
                .contains(quantityOfItemsAfterUpdate);

        softly.assertAll();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Unlock 'Free Shipping' when total exceeds threshold")
    void addItemsUntilFreeShippingTest() {
        String quantityOfItemsBeforeUpdate = "1 Item";
        double itemPrice = 11.99;
        double freeShippingThreshold = 75.0;
        String freeShippingInOrderSummary = "Free";

        cartPage = new ShoppingCartPage(driver);
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.selectProductAndItsSizeFromCatalog();
        mensClothesPage.clickToBagButton();
        mensClothesPage.clickViewButton();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(cartPage.getQuantityOfItemsText())
                .as("Initial quantity should be 1")
                .contains(quantityOfItemsBeforeUpdate);

        cartPage.editItemButton();
        cartPage.movingToElementUpdateBagButton();

        double total = itemPrice;

        while (total < freeShippingThreshold) {
            cartPage.addItemToBag();
            total += itemPrice;
        }

        cartPage.updateBag();

        softly.assertThat(cartPage.isFreeShippingMessageDisplayed())
                .as("Free shipping message should be displayed after reaching threshold")
                .isTrue();

        String displayedTotal = cartPage.getFreeShippingTextInOrderSummary();

        softly.assertThat(displayedTotal)
                .as("Total should be equal or greater than free shipping threshold")
                .isEqualTo(freeShippingInOrderSummary);

        softly.assertAll();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Verify maximum quantity of items allowed in cart")
    void addMaximumQuantityToCartTest() {
        int expectedMaxItemCount = 9;

        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.selectProductAndItsSizeFromCatalog();

        int actualItemCount = mensClothesPage.addItemsUntilButtonDisabled();

        assertEquals(expectedMaxItemCount, actualItemCount,
                "User should not be able to add more than max allowed items");

        assertFalse(mensClothesPage.isAddToCartButtonEnabled(),
                "Add to Cart button should be disabled at max quantity");
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Remove item from cart and verify it's empty")
    void removeItemFromCartTest() {
        String expectedQuantityText = "1 Item";
        String expectedEmptyCartMessage = "Your bag is empty. Find something you love!";

        cartPage = new ShoppingCartPage(driver);
        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.selectProductAndItsSizeFromCatalog();

        mensClothesPage.clickToBagButton();
        mensClothesPage.clickViewButton();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(cartPage.getQuantityOfItemsText())
                .as("Cart should contain 1 item")
                .contains(expectedQuantityText);

        softly.assertThat(cartPage.getProductName())
                .as("Product name should be visible in cart")
                .isNotEmpty();

        cartPage.removeProductInBag();

        softly.assertThat(cartPage.getItemEmptyText())
                .as("Cart should be empty after removing the item")
                .isEqualTo(expectedEmptyCartMessage);

        softly.assertAll();
    }
}
