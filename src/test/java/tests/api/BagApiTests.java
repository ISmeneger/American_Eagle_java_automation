package tests.api;

import controller.BagController;
import dto.BagResponse;
import extensions.GuestTokenExtension;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.AllureExtension;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("API")
@ExtendWith(GuestTokenExtension.class)
@ExtendWith(AllureExtension.class)
class BagApiTests {
    private final BagController bag = new BagController();

    private static final String TEST_SKU_ID = "0043023894"; //AE Mariner Jacket
    private static final String TEST_SKU_ID_2 = "0043421411";

    @BeforeEach
    void clearBagBeforeEachTest() {
        BagResponse currentBag = bag.getBag();
        for (BagResponse.Item item : currentBag.getData().getItems()) {
            bag.deleteItem(item.getItemId())
                    .then()
                    .statusCode(202);
        }
    }

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Check add product to cart")
    void addItemTest() {
        int qty = 1;

        BagResponse beforeAdd = bag.getBag();
        assertThat(beforeAdd.getData().getItemCount())
                .as("Cart must be empty before adding")
                .isZero();

        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();
        assertThat(afterAdd.getData().getItemCount())
                .as("After adding one item, the cart should contain %d product(s)", qty)
                .isEqualTo(qty);

        BagResponse finalBag = bag.getBag();
        assertThat(finalBag.getData().getItems())
                .as("The cart must contain a product with the required SKU")
                .anySatisfy(item -> {
                    assertThat(item.getSku()).isEqualTo(TEST_SKU_ID);
                    assertThat(item.getQuantity()).isEqualTo(qty);
                });
    }

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Check get product in cart")
    void getItemTest() {
        int qty = 1;
        String productName = "AE Mariner Jacket";
        String size = "XS";
        double originalPrice = 89.95;
        double price = 62.96;

        BagResponse beforeAdd = bag.getBag();
        assertThat(beforeAdd.getData().getItemCount())
                .as("Cart must be empty before adding")
                .isZero();

        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .as("After adding one item, the cart should contain %d product(s)", qty)
                .isEqualTo(qty);

        BagResponse finalBag = bag.getBag();

        assertThat(finalBag.getData().getItems())
                .as("The cart must contain a product with the specified parameters")
                .anySatisfy(item -> {
                    assertThat(item.getSku())
                            .as("Product SKU must be %s", TEST_SKU_ID)
                            .isEqualTo(TEST_SKU_ID);
                    assertThat(item.getQuantity())
                            .as("The quantity of the product must be %d", qty)
                            .isEqualTo(qty);
                    assertThat(item.getProductName())
                            .as("Product name must be %s", productName)
                            .isEqualTo(productName);
                    assertThat(item.getSize())
                            .as("Product size must be %s", size)
                            .isEqualTo(size);
                    assertThat(item.getOriginalPrice())
                            .as("The original price of the product must be %.2f", originalPrice)
                            .isEqualTo(originalPrice);
                    assertThat(item.getPrice())
                            .as("The price of the product must be %.2f", price)
                            .isEqualTo(price);
                });
    }

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Check update product in cart")
    void updateItemTest() {
        int initialQty = 1;
        int updatedQty = 2;

        bag.addItem(TEST_SKU_ID, initialQty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();
        assertThat(afterAdd.getData().getItemCount())
                .as("Expected %d item(s) after adding", initialQty)
                .isEqualTo(initialQty);

        String itemId = afterAdd.getData().getItems().get(0).getItemId();

        bag.updateItem(TEST_SKU_ID, updatedQty, itemId)
                .then()
                .statusCode(202);

        BagResponse afterUpdate = bag.getBag();
        assertThat(afterUpdate.getData().getItems())
                .as("The cart must contain a product with SKU %s and quantity %d", TEST_SKU_ID, updatedQty)
                .anySatisfy(item -> {
                    assertThat(item.getSku())
                            .as("SKU must be %s", TEST_SKU_ID)
                            .isEqualTo(TEST_SKU_ID);
                    assertThat(item.getQuantity())
                            .as("Expected quantity %d", updatedQty)
                            .isEqualTo(updatedQty);
                });
    }

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Check delete product in cart")
    void deleteItemTest() {
        int qty = 1;

        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .as("After adding one item, the cart should contain %d product(s)", qty)
                .isEqualTo(qty);

        BagResponse.Item itemToDelete = afterAdd.getData().getItems().stream()
                .filter(i -> i.getSku().equals(TEST_SKU_ID))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Product with SKU " + TEST_SKU_ID + " not found in cart"));

        String itemId = itemToDelete.getItemId();

        bag.deleteItem(itemId)
                .then()
                .statusCode(202);

        BagResponse afterDelete = bag.getBag();
        assertThat(afterDelete.getData().getItemCount())
                .as("The recycle bin was expected to be empty after deletion")
                .isZero();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Check get different products in cart")
    void addAndGetItemsTest() {
        int qty = 1;
        int expectedTotalQty = 2;

        BagResponse beforeAdd = bag.getBag();
        assertThat(beforeAdd.getData().getItemCount())
                .as("Cart must be empty before adding")
                .isZero();

        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        bag.addItem(TEST_SKU_ID_2, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();
        assertThat(afterAdd.getData().getItemCount())
                .as("There must be %d item(s) in your cart", expectedTotalQty)
                .isEqualTo(expectedTotalQty);

        assertThat(afterAdd.getData().getItems())
                .extracting(BagResponse.Item::getSku)
                .as("Cart must contain both SKUs")
                .containsExactlyInAnyOrder(TEST_SKU_ID, TEST_SKU_ID_2);
    }
}
