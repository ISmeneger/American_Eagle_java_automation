package tests.api;

import api.controller.ProductController;
import extensions.GuestTokenExtension;
import io.qameta.allure.Severity;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("API")
@ExtendWith(GuestTokenExtension.class)
public class ProductApiTests {

    private static final String TEST_CATEGORY_ID = "cat10025";

    private final ProductController productController =
            new ProductController();

    @Test
    @Severity(CRITICAL)
    @Tags({
            @Tag("smoke"),
            @Tag("positive")
    })
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
                .isNotEmpty();
    }

    @Test
    @Severity(CRITICAL)
    @Tags({
            @Tag("extended"),
            @Tag("negative")
    })
    @DisplayName("Get products by category without Authorization")
    void getProductsByCategoryWithoutAuthorizationTest() {

        Response response =
                productController
                        .getProductsByCategoryWithoutAuthorization(
                                TEST_CATEGORY_ID
                        );

        response.then()
                .statusCode(401);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({
            @Tag("smoke"),
            @Tag("positive")
    })
    @DisplayName("Get inventory by product ID")
    void getInventoryByProductTest() {

        String productId =
                productController.getAvailableProductIds(TEST_CATEGORY_ID)
                        .get(0);

        Response response =
                productController.getInventoryByProduct(productId);

        response.then()
                .statusCode(200);

        List<String> skuIds = response.jsonPath()
                .getList(
                        "data.'" + productId + "'.skuId",
                        String.class
                );

        assertThat(skuIds)
                .as("Product inventory must contain SKU IDs")
                .isNotEmpty();

        assertThat(skuIds)
                .as("SKU IDs must not contain blank values")
                .allSatisfy(skuId ->
                        assertThat(skuId).isNotBlank()
                );
    }

    @Test
    @Severity(NORMAL)
    @Tags({
            @Tag("extended"),
            @Tag("positive")
    })
    @DisplayName("Get available product IDs from category")
    void getAvailableProductIdsTest() {

        List<String> productIds =
                productController.getAvailableProductIds(TEST_CATEGORY_ID);

        assertThat(productIds)
                .as("Category must contain available products")
                .isNotEmpty();
    }

    @Test
    @Severity(NORMAL)
    @Tags({
            @Tag("extended"),
            @Tag("positive")
    })
    @DisplayName("Get first SKU from available product")
    void getFirstSkuFromAvailableProductsTest() {

        String skuId =
                productController.getFirstSkuFromAvailableProducts(
                        TEST_CATEGORY_ID
                );

        assertThat(skuId)
                .as("SKU ID must not be empty")
                .isNotBlank();
    }

    @Test
    @Severity(NORMAL)
    @Tags({
            @Tag("extended"),
            @Tag("positive")
    })
    @DisplayName("Find available product with at least two SKUs")
    void getProductWithAtLeastTwoSkusTest() {

        String productId =
                productController.getProductWithAtLeastTwoSkus(
                        TEST_CATEGORY_ID
                );

        assertThat(productId)
                .as("Product ID must not be empty")
                .isNotBlank();
    }

    @Test
    @Severity(NORMAL)
    @Tags({
            @Tag("extended"),
            @Tag("negative")
    })
    @DisplayName("Get inventory with invalid product ID")
    void getInventoryWithInvalidProductIdTest() {

        String invalidProductId = "invalid-product-id";

        Response response =
                productController.getInventoryByProduct(invalidProductId);

        response.then()
                .statusCode(400);

        assertThat(
                response.jsonPath().getString("error.status")
        )
                .as("Error status must be 400")
                .isEqualTo("400");

        Object data = response.jsonPath().get("data");

        assertThat(data)
                .as("Data must be null for invalid product ID")
                .isNull();

        List<String> errorFields = response.jsonPath()
                .getList(
                        "error.errors[0].fields",
                        String.class
                );

        assertThat(errorFields)
                .as("Error must be related to productIds")
                .contains("productIds");
    }

    @Test
    @Severity(NORMAL)
    @Tags({
            @Tag("extended"),
            @Tag("negative")
    })
    @DisplayName("Get products with invalid category ID")
    void getProductsWithInvalidCategoryIdTest() {

        String invalidCategoryId = "invalid-category-id";

        Response response =
                productController.getProductsByCategory(invalidCategoryId);

        response.then()
                .statusCode(404);

        assertThat(
                response.jsonPath().getString("errors[0].code")
        )
                .as("Error code must indicate an empty product list")
                .isEqualTo("error.browse.emptyProductList");

        assertThat(
                response.jsonPath().getString("errors[0].title")
        )
                .as("Error title must indicate that no products were found")
                .isEqualTo("No Products Found");

        List<String> errorFields = response.jsonPath()
                .getList(
                        "errors[0].meta.fields",
                        String.class
                );

        assertThat(errorFields)
                .as("Error must be related to categoryId")
                .contains("categoryId");
    }

    @Test
    @Severity(CRITICAL)
    @Tags({
            @Tag("extended"),
            @Tag("negative")
    })
    @DisplayName("Get inventory by product ID without Authorization")
    void getInventoryByProductWithoutAuthorizationTest() {

        String productId =
                productController
                        .getAvailableProductIds(TEST_CATEGORY_ID)
                        .get(0);

        Response response =
                productController
                        .getInventoryByProductWithoutAuthorization(
                                productId
                        );

        response.then()
                .statusCode(401);
    }
}