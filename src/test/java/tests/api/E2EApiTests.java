package tests.api;

import api.controller.BagController;
import api.controller.ProductController;
import dto.BagResponse;
import extensions.GuestTokenExtension;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.assertj.core.api.Assertions.assertThat;

@Tags({
        @Tag("API"),
        @Tag("E2E")
})
@ExtendWith(GuestTokenExtension.class)
public class E2EApiTests {

    private static final String TEST_CATEGORY_ID = "cat10025";

    private BagController bag;
    private ProductController product;

    @BeforeEach
    void setUp() {

        bag = new BagController();
        product = new ProductController();

        BagResponse currentBag = bag.getBag();

        for (BagResponse.Item item : currentBag.getData().getItems()) {
            bag.deleteItem(item.getItemId())
                    .then()
                    .statusCode(202);
        }

        BagResponse emptyBag = bag.getBag();

        assertThat(emptyBag.getData().getItems())
                .as("Cart must be empty before E2E test")
                .isEmpty();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("positive")
    @DisplayName("Guest user completes full shopping bag lifecycle")
    void guestUserShoppingBagLifecycleTest() {

        int initialQty = 1;
        int updatedQty = 2;

        // 1. Find an available product SKU
        String skuId =
                product.getFirstSkuFromAvailableProducts(TEST_CATEGORY_ID);

        assertThat(skuId)
                .as("Available SKU must be found")
                .isNotBlank();

        // 2. Add product to bag
        bag.addItem(skuId, initialQty)
                .then()
                .statusCode(202);

        // 3. Verify product was added
        BagResponse afterAdd = bag.getBag();

        BagResponse.Item addedItem = afterAdd.getData().getItems().stream()
                .filter(item -> skuId.equals(item.getSku()))
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError(
                                "Product with SKU " + skuId + " not found in cart"
                        )
                );

        assertThat(addedItem.getQuantity())
                .as("Initial product quantity must be %d", initialQty)
                .isEqualTo(initialQty);

        String itemId = addedItem.getItemId();

        assertThat(itemId)
                .as("Added item must have itemId")
                .isNotBlank();

        // 4. Update product quantity
        bag.updateItem(skuId, updatedQty, itemId)
                .then()
                .statusCode(202);

        // 5. Verify updated quantity
        BagResponse afterUpdate = bag.getBag();

        BagResponse.Item updatedItem = afterUpdate.getData().getItems().stream()
                .filter(item -> itemId.equals(item.getItemId()))
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError(
                                "Item with ID " + itemId + " not found after update"
                        )
                );

        assertThat(updatedItem.getSku())
                .as("SKU must remain unchanged after quantity update")
                .isEqualTo(skuId);

        assertThat(updatedItem.getQuantity())
                .as("Product quantity must be updated to %d", updatedQty)
                .isEqualTo(updatedQty);

        // 6. Delete product
        bag.deleteItem(itemId)
                .then()
                .statusCode(202);

        // 7. Verify product was deleted
        BagResponse afterDelete = bag.getBag();

        assertThat(afterDelete.getData().getItems())
                .as("Deleted item must not remain in the cart")
                .noneMatch(item -> itemId.equals(item.getItemId()));

        assertThat(afterDelete.getData().getItems())
                .as("Deleted SKU must not remain in the cart")
                .noneMatch(item -> skuId.equals(item.getSku()));
    }
}