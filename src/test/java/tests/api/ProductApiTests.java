package tests.api;

import api.controller.ProductController;
import extensions.GuestTokenExtension;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(GuestTokenExtension.class)
public class ProductApiTests {

    private static final String TEST_CATEGORY_ID = "cat10025";

    private final ProductController productController =
            new ProductController();

    @Test
    @DisplayName("Get products by category")
    void getProductsByCategoryTest() {

        Response response =
                productController.getProductsByCategory(TEST_CATEGORY_ID);

        response.then()
                .statusCode(200);

        List<String> productIds = response.jsonPath()
                .getList(
                        "data.relationships.products.data.id",
                        String.class
                );

        assertThat(productIds)
                .as("Category must contain products")
                .isNotNull()
                .isNotEmpty();

        System.out.println(
                "PRODUCT COUNT: " + productIds.size()
        );

        System.out.println(
                "FIRST PRODUCT ID: " + productIds.get(0)
        );
    }

    @Test
    @DisplayName("Get available product IDs from category")
    void getAvailableProductIdsTest() {

        List<String> productIds =
                productController.getAvailableProductIds(TEST_CATEGORY_ID);

        System.out.println(
                "AVAILABLE PRODUCT COUNT: " + productIds.size()
        );

        System.out.println(
                "AVAILABLE PRODUCT IDS: " + productIds
        );

        assertThat(productIds)
                .as("Category must contain available products")
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    @DisplayName("Get first SKU from available product")
    void getFirstSkuFromAvailableProductsTest() {

        String skuId =
                productController.getFirstSkuFromAvailableProducts(
                        TEST_CATEGORY_ID
                );

        System.out.println(
                "FOUND AVAILABLE SKU: " + skuId
        );

        assertThat(skuId)
                .as("SKU ID must not be empty")
                .isNotBlank();
    }

    @Test
    @DisplayName("Find available product with at least two SKUs")
    void getProductWithAtLeastTwoSkusTest() {

        String productId =
                productController.getProductWithAtLeastTwoSkus(
                        TEST_CATEGORY_ID
                );

        System.out.println(
                "FOUND PRODUCT WITH AT LEAST TWO SKUS: " + productId
        );

        assertThat(productId)
                .as("Product ID must not be empty")
                .isNotBlank();
    }
}