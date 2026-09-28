package tests.ui;

import io.qameta.allure.Severity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.MensClothesPage;
import pages.ProductPage;
import pages.ShoppingCartPage;
import steps.ProductCatalogSteps;


import java.math.BigDecimal;

import static constants.CommonConstants.*;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.*;

@Tags({
        @Tag("UI"),
        @Tag("extended")
})
class MensClothesChapterUnauthorizedUserTests extends BaseTest {

    HomePage homePage;
    MensClothesPage mensClothesPage;
    ProductPage productPage;
    ShoppingCartPage cartPage;
    ProductCatalogSteps productCatalogSteps;

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        homePage.closeBlockingOverlaysIfAvailable();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Verify Men's Clothes page is opened correctly")
    void checkMensFormPageTest() {

        mensClothesPage = new MensClothesPage(driver);

        mensClothesPage.movingToElementMen();
        mensClothesPage.selectChapterViewAll();
        mensClothesPage.closePopUpIfAvailable();

        assertAll(
                () -> assertEquals(
                        SUB_TITLE_TEXT,
                        mensClothesPage.getMensPageTitle(),
                        "Sub-title should match expected text"
                ),
                () -> assertEquals(
                        BASE_URL + CURRENT_MEN_URL,
                        mensClothesPage.getCurrentUrl(),
                        "Current URL should match expected men's clothing URL"
                )
        );
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Add item from catalog to cart and verify success message is shown")
    void addItemFromCatalogToCartTest() {

        productCatalogSteps = new ProductCatalogSteps(driver);
        productPage = new ProductPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfPresent();
        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();

        String actualMessage =
                productPage.getSuccessfulAddedToBagText();

        assertEquals(
                SUCCESSFUL_ADDED_TO_BAG,
                actualMessage,
                "Success message text should match expected"
        );
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Check that product price in catalog matches the price in cart")
    void priceMatchesBetweenCatalogAndCartTest() {

        String expectedQuantityText = "1 Item";

        productCatalogSteps = new ProductCatalogSteps(driver);
        productPage = new ProductPage(driver);
        cartPage = new ShoppingCartPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfPresent();

        String productPriceRaw =
                productPage.getProductPrice();

        String productPrice = productPriceRaw
                .replace("Now", "")
                .trim();

        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();
        productPage.openShoppingBag();

        String cartPrice =
                cartPage.getCartPrice();

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

        softly.assertThat(cartPrice)
                .as("Product price in cart should match product page price")
                .isEqualTo(productPrice);

        softly.assertAll();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Selected product size is preserved in cart")
    void selectedProductSizeMatchesCartSizeTest() {

        productCatalogSteps = new ProductCatalogSteps(driver);
        productPage = new ProductPage(driver);
        cartPage = new ShoppingCartPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfPresent();

        productPage.selectFirstAvailableSize();

        String selectedSize =
                productPage.getSelectedSize();

        productPage.clickAddToBagButton();
        productPage.openShoppingBag();

        String cartSize =
                cartPage.getProductSize();

        assertEquals(
                selectedSize,
                cartSize,
                "Selected product size should be preserved in the cart"
        );
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Add item to bag, change quantity and verify subtotal")
    void addItemAndChangeQuantityInBagTest() {

        String quantityOfItemsBeforeUpdate = "1 Item";
        String quantityOfItemsAfterUpdate = "2 Items";
        int expectedQuantity = 2;

        productCatalogSteps = new ProductCatalogSteps(driver);
        productPage = new ProductPage(driver);
        cartPage = new ShoppingCartPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfPresent();

        String productPriceRaw =
                productPage.getProductPrice();

        BigDecimal itemPrice = new BigDecimal(
                productPriceRaw
                        .replace("Now", "")
                        .replace("$", "")
                        .replace(",", "")
                        .trim()
        );

        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();
        productPage.openShoppingBag();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(cartPage.getQuantityOfItemsText())
                .as("Initial quantity should be 1")
                .contains(quantityOfItemsBeforeUpdate);

        cartPage.editItemButton();
        cartPage.movingToElementUpdateBagButton();
        cartPage.increaseProductQuantity();
        cartPage.updateBag();

        softly.assertThat(cartPage.getQuantityOfItemsText())
                .as("Quantity after update should be 2")
                .contains(quantityOfItemsAfterUpdate);

        BigDecimal expectedSubtotal =
                itemPrice.multiply(
                        BigDecimal.valueOf(expectedQuantity)
                );

        String subtotalRaw =
                cartPage.getSubtotalText();

        BigDecimal actualSubtotal = new BigDecimal(
                subtotalRaw
                        .replace("$", "")
                        .replace(",", "")
                        .trim()
        );

        softly.assertThat(actualSubtotal)
                .as("Subtotal should equal product price multiplied by quantity")
                .isEqualByComparingTo(expectedSubtotal);

        softly.assertAll();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Unlock 'Free Shipping' when total exceeds threshold")
    void addItemsUntilFreeShippingTest() {

        String quantityOfItemsBeforeUpdate = "1 Item";
        double freeShippingThreshold = 75.0;

        productCatalogSteps = new ProductCatalogSteps(driver);
        productPage = new ProductPage(driver);
        cartPage = new ShoppingCartPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfPresent();

        String productPriceRaw =
                productPage.getProductPrice();

        double itemPrice = Double.parseDouble(
                productPriceRaw
                        .replace("Now", "")
                        .replace("$", "")
                        .replace(",", "")
                        .trim()
        );

        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();
        productPage.openShoppingBag();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(cartPage.getQuantityOfItemsText())
                .as("Initial quantity should be 1")
                .contains(quantityOfItemsBeforeUpdate);

        cartPage.editItemButton();
        cartPage.movingToElementUpdateBagButton();

        double total = itemPrice;

        while (total < freeShippingThreshold) {
            cartPage.increaseProductQuantity();
            total += itemPrice;
        }

        cartPage.updateBag();

        softly.assertThat(cartPage.isFreeShippingMessageDisplayed())
                .as("Free shipping message should be displayed after reaching threshold")
                .isTrue();

        softly.assertAll();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Verify maximum quantity of items allowed in cart")
    void addMaximumQuantityToCartTest() {

        int expectedMaxQuantity = 10;

        productCatalogSteps = new ProductCatalogSteps(driver);
        productPage = new ProductPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfPresent();
        productPage.selectFirstAvailableSize();

        int actualMaxQuantity =
                productPage.increaseQuantityUntilDisabled();

        assertAll(
                () -> assertEquals(
                        expectedMaxQuantity,
                        actualMaxQuantity,
                        "Maximum allowed product quantity should be 10"
                ),
                () -> assertFalse(
                        productPage.isIncreaseQuantityButtonEnabled(),
                        "Increase quantity button should be disabled " +
                                "at maximum quantity"
                )
        );
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Remove item from cart")
    void removeItemFromCartTest() {

        String expectedEmptyBagMessage =
                "Your bag is empty. Find something you love!";

        productCatalogSteps = new ProductCatalogSteps(driver);
        productPage = new ProductPage(driver);
        cartPage = new ShoppingCartPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfPresent();
        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();
        productPage.openShoppingBag();

        String productNameBeforeRemoving =
                cartPage.getProductName();

        assertFalse(
                productNameBeforeRemoving.isEmpty(),
                "Product name in cart should not be empty before removing"
        );

        cartPage.removeProductInBag();

        String actualEmptyBagMessage =
                cartPage.getItemEmptyText();

        assertEquals(
                expectedEmptyBagMessage,
                actualEmptyBagMessage,
                "Empty bag message should match expected"
        );
    }
}