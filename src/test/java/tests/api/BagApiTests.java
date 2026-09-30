package tests.api;

import api.controller.BagController;
import api.controller.ProductController;
import dto.BagResponse;
import extensions.GuestTokenExtension;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(GuestTokenExtension.class)
class BagApiTests {

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

        assertThat(emptyBag.getData().getItemCount())
                .as("Cart must be empty before test")
                .isZero();

        assertThat(emptyBag.getData().getItems())
                .as("Cart items list must be empty before test")
                .isEmpty();
    }

    @Test
    @Severity(CRITICAL)
    @Tag("smoke")
    @DisplayName("Check add product to cart")
    void addItemTest() {
        int qty = 1;

        String skuId =
                product.getFirstSkuFromAvailableProducts(TEST_CATEGORY_ID);

        bag.addItem(skuId, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .as("After adding one item, the cart should contain %d product(s)", qty)
                .isEqualTo(qty);

        assertThat(afterAdd.getData().getItems())
                .as("The cart must contain a product with the required SKU")
                .anySatisfy(item -> {
                    assertThat(item.getSku())
                            .as("SKU must match added SKU")
                            .isEqualTo(skuId);

                    assertThat(item.getQuantity())
                            .as("Product quantity must be %d", qty)
                            .isEqualTo(qty);
                });
    }

    @Test
    @Severity(CRITICAL)
    @Tag("smoke")
    @DisplayName("Check get product in cart")
    void getItemTest() {
        int qty = 1;

        String skuId =
                product.getFirstSkuFromAvailableProducts(TEST_CATEGORY_ID);

        bag.addItem(skuId, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .as("After adding one item, the cart should contain %d product(s)", qty)
                .isEqualTo(qty);

        assertThat(afterAdd.getData().getItems())
                .as("The cart must contain the added product")
                .anySatisfy(item -> {
                    assertThat(item.getSku())
                            .as("SKU must match added SKU")
                            .isEqualTo(skuId);

                    assertThat(item.getQuantity())
                            .as("Product quantity must be %d", qty)
                            .isEqualTo(qty);

                    assertThat(item.getProductName())
                            .as("Product name must not be empty")
                            .isNotBlank();

                    assertThat(item.getSize())
                            .as("Product size must not be empty")
                            .isNotBlank();

                    assertThat(item.getOriginalPrice())
                            .as("Original price must be greater than zero")
                            .isPositive();

                    assertThat(item.getPrice())
                            .as("Product price must be greater than zero")
                            .isPositive();
                });
    }

    @Test
    @Severity(CRITICAL)
    @Tag("smoke")
    @DisplayName("Check update product in cart")
    void updateItemTest() {
        int initialQty = 1;
        int updatedQty = 2;

        String skuId =
                product.getFirstSkuFromAvailableProducts(TEST_CATEGORY_ID);

        bag.addItem(skuId, initialQty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .as("Expected %d item(s) after adding", initialQty)
                .isEqualTo(initialQty);

        BagResponse.Item addedItem = afterAdd.getData().getItems().stream()
                .filter(item -> item.getSku().equals(skuId))
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError(
                                "Product with SKU " + skuId + " not found in cart"
                        )
                );

        String itemId = addedItem.getItemId();

        bag.updateItem(skuId, updatedQty, itemId)
                .then()
                .statusCode(202);

        BagResponse afterUpdate = bag.getBag();

        assertThat(afterUpdate.getData().getItems())
                .as(
                        "The cart must contain a product with SKU %s and quantity %d",
                        skuId,
                        updatedQty
                )
                .anySatisfy(item -> {
                    assertThat(item.getSku())
                            .as("SKU must be %s", skuId)
                            .isEqualTo(skuId);

                    assertThat(item.getQuantity())
                            .as("Expected quantity %d", updatedQty)
                            .isEqualTo(updatedQty);
                });
    }

    @Test
    @Severity(CRITICAL)
    @Tag("smoke")
    @DisplayName("Check delete product in cart")
    void deleteItemTest() {
        int qty = 1;

        String skuId =
                product.getFirstSkuFromAvailableProducts(TEST_CATEGORY_ID);

        bag.addItem(skuId, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .as("After adding one item, the cart should contain %d product(s)", qty)
                .isEqualTo(qty);

        BagResponse.Item itemToDelete = afterAdd.getData().getItems().stream()
                .filter(item -> item.getSku().equals(skuId))
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError(
                                "Product with SKU " + skuId + " not found in cart"
                        )
                );

        String itemId = itemToDelete.getItemId();

        bag.deleteItem(itemId)
                .then()
                .statusCode(202);

        BagResponse afterDelete = bag.getBag();

        assertThat(afterDelete.getData().getItemCount())
                .as("Cart should be empty after deleting the product")
                .isZero();

        assertThat(afterDelete.getData().getItems())
                .as("Deleted product must not remain in the cart")
                .noneSatisfy(item ->
                        assertThat(item.getSku()).isEqualTo(skuId)
                );
    }

    @Test
    @Severity(CRITICAL)
    @Tag("smoke")
    @DisplayName("Check get different product variants in cart")
    void addAndGetProductVariantsTest() {
        int qty = 1;
        int expectedTotalQty = 2;

        String productId =
                product.getProductWithAtLeastTwoSkus(TEST_CATEGORY_ID);

        List<String> skuIds =
                product.getSkuList(productId);

        assertThat(skuIds)
                .as("Product must contain at least two SKUs")
                .hasSizeGreaterThanOrEqualTo(2);

        String firstSku = skuIds.get(0);
        String secondSku = skuIds.get(1);

        assertThat(firstSku)
                .as("First and second SKU must be different")
                .isNotEqualTo(secondSku);

        bag.addItem(firstSku, qty)
                .then()
                .statusCode(202);

        bag.addItem(secondSku, qty)
                .then()
                .statusCode(202);

        BagResponse afterAdd = bag.getBag();

        assertThat(afterAdd.getData().getItemCount())
                .as("There must be %d item(s) in the cart", expectedTotalQty)
                .isEqualTo(expectedTotalQty);

        assertThat(afterAdd.getData().getItems())
                .extracting(BagResponse.Item::getSku)
                .as("Cart must contain both dynamically selected SKUs")
                .containsExactlyInAnyOrder(firstSku, secondSku);
    }
}