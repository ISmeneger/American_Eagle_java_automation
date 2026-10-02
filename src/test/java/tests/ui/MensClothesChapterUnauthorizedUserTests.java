package tests.ui;

import configs.ConfigProvider;
import io.qameta.allure.Severity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.MensClothesPage;
import pages.ProductPage;
import pages.ShoppingCartPage;
import steps.ProductCatalogSteps;

import java.math.BigDecimal;

import static constants.CommonConstants.ADDED_TO_BAG_MESSAGE;
import static constants.CommonConstants.CART_PATH;
import static constants.CommonConstants.GUEST_CART_TITLE;
import static constants.CommonConstants.MENS_CLOTHES_TITLE;
import static constants.CommonConstants.MEN_PATH;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Tags({
        @Tag("UI"),
        @Tag("extended")
})
class MensClothesChapterUnauthorizedUserTests extends BaseTest {

    private static final String UI_BASE_URL =
            ConfigProvider.get().getUiBaseUrl();

    private static final String ONE_ITEM_TEXT =
            "1 Item";

    private static final String TWO_ITEMS_TEXT =
            "2 Items";

    private static final String EMPTY_BAG_MESSAGE =
            "Your bag is empty. Find something you love!";

    private static final BigDecimal FREE_SHIPPING_THRESHOLD =
            new BigDecimal("75.00");

    private static final int MAX_ALLOWED_QUANTITY = 10;

    @BeforeEach
    void setupPage() {
        HomePage homePage = new HomePage(driver);
        homePage.closeBlockingOverlaysIfAvailable();
    }

    private BigDecimal parsePrice(String priceText) {
        return new BigDecimal(
                priceText
                        .replace("Now", "")
                        .replace("$", "")
                        .replace(",", "")
                        .trim()
        );
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Verify Men's Clothes page is opened correctly")
    void checkMensFormPageTest() {

        MensClothesPage mensClothesPage =
                new MensClothesPage(driver);

        mensClothesPage.moveToMenMenu();
        mensClothesPage.clickViewAllCategories();
        mensClothesPage.closePopUpIfAvailable();

        assertAll(
                () -> assertEquals(
                        MENS_CLOTHES_TITLE,
                        mensClothesPage.getMensPageTitle(),
                        "Sub-title should match expected text"
                ),
                () -> assertEquals(
                        UI_BASE_URL + MEN_PATH,
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

        ProductCatalogSteps productCatalogSteps =
                new ProductCatalogSteps(driver);

        ProductPage productPage =
                new ProductPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfAvailable();
        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();

        String actualMessage =
                productPage.getAddedToBagMessage();

        assertEquals(
                ADDED_TO_BAG_MESSAGE,
                actualMessage,
                "Success message text should match expected"
        );
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Check that product price in catalog matches the price in cart")
    void priceMatchesBetweenCatalogAndCartTest() {

        ProductCatalogSteps productCatalogSteps =
                new ProductCatalogSteps(driver);

        ProductPage productPage =
                new ProductPage(driver);

        ShoppingCartPage cartPage =
                new ShoppingCartPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfAvailable();

        String productPrice =
                productPage.getProductPrice()
                        .replace("Now", "")
                        .trim();

        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();
        productPage.openShoppingBag();

        String cartPrice =
                cartPage.getProductPriceInCart();

        SoftAssertions softly =
                new SoftAssertions();

        softly.assertThat(
                        cartPage.getCurrentUrl()
                )
                .as("User should be on cart page")
                .isEqualTo(UI_BASE_URL + CART_PATH);

        softly.assertThat(
                        cartPage.getCartPageHeader()
                )
                .as("Cart page title should match expected")
                .isEqualTo(GUEST_CART_TITLE);

        softly.assertThat(
                        cartPage.getQuantityOfItemsText()
                )
                .as("Cart should contain 1 item")
                .contains(ONE_ITEM_TEXT);

        softly.assertThat(
                        cartPage.getProductName()
                )
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

        ProductCatalogSteps productCatalogSteps =
                new ProductCatalogSteps(driver);

        ProductPage productPage =
                new ProductPage(driver);

        ShoppingCartPage cartPage =
                new ShoppingCartPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfAvailable();
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

        int expectedQuantity = 2;

        ProductCatalogSteps productCatalogSteps =
                new ProductCatalogSteps(driver);

        ProductPage productPage =
                new ProductPage(driver);

        ShoppingCartPage cartPage =
                new ShoppingCartPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfAvailable();

        BigDecimal itemPrice =
                parsePrice(
                        productPage.getProductPrice()
                );

        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();
        productPage.openShoppingBag();

        SoftAssertions softly =
                new SoftAssertions();

        softly.assertThat(
                        cartPage.getQuantityOfItemsText()
                )
                .as("Initial quantity should be 1")
                .contains(ONE_ITEM_TEXT);

        cartPage.clickEditItemButton();
        cartPage.moveToUpdateBagButton();
        cartPage.increaseProductQuantity();
        cartPage.updateBag();

        softly.assertThat(
                        cartPage.getQuantityOfItemsText()
                )
                .as("Quantity after update should be 2")
                .contains(TWO_ITEMS_TEXT);

        BigDecimal expectedSubtotal =
                itemPrice.multiply(
                        BigDecimal.valueOf(expectedQuantity)
                );

        BigDecimal actualSubtotal =
                parsePrice(
                        cartPage.getSubtotalText()
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

        ProductCatalogSteps productCatalogSteps =
                new ProductCatalogSteps(driver);

        ProductPage productPage =
                new ProductPage(driver);

        ShoppingCartPage cartPage =
                new ShoppingCartPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfAvailable();

        BigDecimal itemPrice =
                parsePrice(
                        productPage.getProductPrice()
                );

        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();
        productPage.openShoppingBag();

        SoftAssertions softly =
                new SoftAssertions();

        softly.assertThat(
                        cartPage.getQuantityOfItemsText()
                )
                .as("Initial quantity should be 1")
                .contains(ONE_ITEM_TEXT);

        cartPage.clickEditItemButton();
        cartPage.moveToUpdateBagButton();

        BigDecimal total = itemPrice;

        while (total.compareTo(FREE_SHIPPING_THRESHOLD) < 0) {
            cartPage.increaseProductQuantity();
            total = total.add(itemPrice);
        }

        cartPage.updateBag();

        softly.assertThat(
                        cartPage.isFreeShippingMessageDisplayed()
                )
                .as("Free shipping message should be displayed after reaching threshold")
                .isTrue();

        softly.assertAll();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Verify maximum quantity of items allowed in cart")
    void addMaximumQuantityToCartTest() {

        ProductCatalogSteps productCatalogSteps =
                new ProductCatalogSteps(driver);

        ProductPage productPage =
                new ProductPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfAvailable();
        productPage.selectFirstAvailableSize();

        int actualMaxQuantity =
                productPage.increaseQuantityUntilDisabled();

        assertAll(
                () -> assertEquals(
                        MAX_ALLOWED_QUANTITY,
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

        ProductCatalogSteps productCatalogSteps =
                new ProductCatalogSteps(driver);

        ProductPage productPage =
                new ProductPage(driver);

        ShoppingCartPage cartPage =
                new ShoppingCartPage(driver);

        productCatalogSteps.openFirstAvailableMensProduct();

        productPage.closePopUpIfAvailable();
        productPage.selectFirstAvailableSize();
        productPage.clickAddToBagButton();
        productPage.openShoppingBag();

        String productNameBeforeRemoving =
                cartPage.getProductName();

        assertFalse(
                productNameBeforeRemoving.isEmpty(),
                "Product name in cart should not be empty before removing"
        );

        cartPage.removeProductFromBag();

        String actualEmptyBagMessage =
                cartPage.getEmptyBagMessage();

        assertEquals(
                EMPTY_BAG_MESSAGE,
                actualEmptyBagMessage,
                "Empty bag message should match expected"
        );
    }
}