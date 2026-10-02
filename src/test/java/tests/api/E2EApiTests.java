package tests.api;

import api.controller.BagController;
import api.controller.ProductController;
import dto.BagResponse;
import extensions.GuestTokenExtension;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Duration;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Tags({
        @Tag("API"),
        @Tag("E2E")
})
@ExtendWith(GuestTokenExtension.class)
public class E2EApiTests {

    private static final String TEST_CATEGORY_ID = "cat10025";

    private BagController bagController;
    private ProductController productController;

    @BeforeEach
    void setUp() {

        bagController = new BagController();
        productController = new ProductController();

        BagResponse currentBag = bagController.getBag();

        for (BagResponse.Item item : currentBag.getData().getItems()) {
            bagController.deleteItem(item.getItemId())
                    .then()
                    .statusCode(202);
        }

        BagResponse emptyBag = bagController.getBag();

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
                productController.getFirstSkuFromAvailableProducts(
                        TEST_CATEGORY_ID
                );

        assertThat(skuId)
                .as("Available SKU must be found")
                .isNotBlank();

        // 2. Add product to bag
        bagController.addItem(skuId, initialQty)
                .then()
                .statusCode(202);

        // 3. Verify product was added
        BagResponse afterAdd = bagController.getBag();

        BagResponse.Item addedItem =
                afterAdd.getData()
                        .getItems()
                        .stream()
                        .filter(item -> skuId.equals(item.getSku()))
                        .findFirst()
                        .orElseThrow(() ->
                                new AssertionError(
                                        "Product with SKU "
                                                + skuId
                                                + " not found in cart"
                                )
                        );

        assertThat(addedItem.getQuantity())
                .as("Initial product quantity must be %d", initialQty)
                .isEqualTo(initialQty);

        String initialItemId = addedItem.getItemId();

        assertThat(initialItemId)
                .as("Added item must have itemId")
                .isNotBlank();

        // 4. Update product quantity
        bagController.updateItem(
                        skuId,
                        updatedQty,
                        initialItemId
                )
                .then()
                .statusCode(202);

        // 5. Verify updated quantity
        await()
                .atMost(Duration.ofSeconds(5))
                .pollInterval(Duration.ofMillis(500))
                .untilAsserted(() -> {

                    BagResponse afterUpdate =
                            bagController.getBag();

                    BagResponse.Item updatedItem =
                            afterUpdate.getData()
                                    .getItems()
                                    .stream()
                                    .filter(item ->
                                            skuId.equals(item.getSku())
                                    )
                                    .findFirst()
                                    .orElseThrow(() ->
                                            new AssertionError(
                                                    "Product with SKU "
                                                            + skuId
                                                            + " not found after update"
                                            )
                                    );

                    assertThat(updatedItem.getSku())
                            .as("SKU must remain unchanged after quantity update")
                            .isEqualTo(skuId);

                    assertThat(updatedItem.getQuantity())
                            .as(
                                    "Product quantity must be updated to %d",
                                    updatedQty
                            )
                            .isEqualTo(updatedQty);
                });

        // Get current itemId after update because API may regenerate it
        BagResponse afterUpdate = bagController.getBag();

        BagResponse.Item updatedItem =
                afterUpdate.getData()
                        .getItems()
                        .stream()
                        .filter(item -> skuId.equals(item.getSku()))
                        .findFirst()
                        .orElseThrow(() ->
                                new AssertionError(
                                        "Product with SKU "
                                                + skuId
                                                + " not found before delete"
                                )
                        );

        String currentItemId = updatedItem.getItemId();

        assertThat(currentItemId)
                .as("Updated item must have itemId")
                .isNotBlank();

        // 6. Delete product
        bagController.deleteItem(currentItemId)
                .then()
                .statusCode(202);

        // 7. Verify product was deleted
        BagResponse afterDelete = bagController.getBag();

        assertThat(afterDelete.getData().getItems())
                .as("Deleted item must not remain in the cart")
                .noneMatch(item ->
                        currentItemId.equals(item.getItemId())
                );

        assertThat(afterDelete.getData().getItems())
                .as("Deleted SKU must not remain in the cart")
                .noneMatch(item ->
                        skuId.equals(item.getSku())
                );
    }
}