package tests.api;

import controller.BagController;
import dto.BagResponse;
import extensions.GuestTokenExtension;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(GuestTokenExtension.class)
class BagApiTests {
    private final BagController bag = new BagController();

    private static final String TEST_SKU_ID = "0043023894"; //AE Mariner Jacket
    private static final String TEST_SKU_ID_2 = "0043421387"; //AE Baggy Pleated Jean

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Checking adding product in bag")
    void addItemTest() {
        int qty = 1;

        BagResponse beforeAdd = bag.getBag();
        assertThat(beforeAdd.getData().getItemCount())
                .isZero();

        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .isEqualTo(qty);
    }

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Checking getting product in bag")
    void getItemTest() {
        int qty = 1;
        String productName = "AE Mariner Jacket";
        String size = "XS";
        double originalPrice = 89.95;
        double price = 62.96;

        BagResponse beforeAdd = bag.getBag();
        assertThat(beforeAdd.getData().getItemCount())
                .isZero();

        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .isEqualTo(qty);

        assertThat(afterAdd.getData().getItems())
                .as("Bag should contain item with sku %s, quantity %d, product name %s, size %s, price %.2f," +
                        " original price %.2f", TEST_SKU_ID, qty, productName, size, originalPrice, price)
                .anySatisfy(item -> {
                    assertThat(item.getSku()).isEqualTo(TEST_SKU_ID);
                    assertThat(item.getQuantity()).isEqualTo(qty);
                    assertThat(item.getProductName()).isEqualTo(productName);
                    assertThat(item.getSize()).isEqualTo(size);
                    assertThat(item.getOriginalPrice()).isEqualTo(originalPrice);
                    assertThat(item.getPrice()).isEqualTo(price);
                });
    }

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Checking updating product in bag")
    void updateItemTest() {
        int qty = 1;
        int updateQty = 2;
        String itemId;

        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .isEqualTo(qty);

        itemId = afterAdd.getData().getItems().get(0).getItemId();

        bag.updateItem(TEST_SKU_ID, updateQty, itemId)
                .then()
                .statusCode(202);
        BagResponse afterUpdate = bag.getBag();

        assertThat(afterUpdate.getData().getItems())
                .as("Bag should contain item with sku %s and quantity %d", TEST_SKU_ID, updateQty)
                .anySatisfy(item -> {
                    assertThat(item.getSku()).isEqualTo(TEST_SKU_ID);
                    assertThat(item.getQuantity()).isEqualTo(updateQty);
                });
    }

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Checking deleting product in bag")
    void deleteItemTest() {
        int qty = 1;
        String itemId;

        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .isEqualTo(qty);

        itemId = afterAdd.getData().getItems().get(0).getItemId();

        bag.deleteItem(itemId)
                .then()
                .statusCode(202);

        BagResponse afterDelete = bag.getBag();
        assertThat(afterDelete.getData().getItemCount())
                .isZero();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Checking getting different products in bag")
    void addAndGetItemsTest() {
        int qty = 1;
        int sumQty = 2;

        BagResponse beforeAdd = bag.getBag();
        assertThat(beforeAdd.getData().getItemCount())
                .isZero();

        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        bag.addItem(TEST_SKU_ID_2, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .isEqualTo(sumQty);
    }
}
