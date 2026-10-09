package api.controller;

import configs.TestPropertiesConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import support.TokenManager;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ProductController {

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                    + "AppleWebKit/537.36 (KHTML, like Gecko) "
                    + "Chrome/153.0.0.0 Safari/537.36";

    private static final String INVENTORY_ENDPOINT =
            "/ugp-api/inventory/v1/groupByProduct/US/";

    private static final String CATEGORY_ENDPOINT =
            "/ugp-api/browse/v1/category/";

    private final RequestSpecification requestSpec;

    public ProductController() {

        TestPropertiesConfig configProperties =
                ConfigFactory.create(
                        TestPropertiesConfig.class,
                        System.getProperties()
                );

        requestSpec = given()
                .baseUri(configProperties.getApiBaseUrl())
                .header("User-Agent", USER_AGENT)
                .header("aecountry", "US")
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header(
                        "Authorization",
                        "Bearer " + TokenManager.getToken()
                )
                .filter(new AllureRestAssured());
    }

    @Step("Get inventory for product: {productId}")
    public Response getInventoryByProduct(String productId) {

        return given(requestSpec)
                .header("Accept", "application/json")
                .header("Referer", "https://www.ae.com/")
                .header("Origin", "https://www.ae.com")
                .when()
                .get(INVENTORY_ENDPOINT + productId)
                .andReturn();
    }

    @Step("Get inventory for product without Authorization: {productId}")
    public Response getInventoryByProductWithoutAuthorization(
            String productId
    ) {

        TestPropertiesConfig configProperties =
                ConfigFactory.create(
                        TestPropertiesConfig.class,
                        System.getProperties()
                );

        return given()
                .baseUri(configProperties.getApiBaseUrl())
                .header("User-Agent", USER_AGENT)
                .header("aecountry", "US")
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header("Accept", "application/json")
                .header("Referer", "https://www.ae.com/")
                .header("Origin", "https://www.ae.com")
                .filter(new AllureRestAssured())
                .when()
                .get(INVENTORY_ENDPOINT + productId)
                .andReturn();
    }

    @Step("Get SKU list for product: {productId}")
    public List<String> getSkuList(String productId) {

        Response response = getInventoryByProduct(productId);

        response.then()
                .statusCode(200);

        return response.jsonPath()
                .getList(
                        "data.'" + productId + "'.skuId",
                        String.class
                );
    }

    @Step("Get products by category: {categoryId}")
    public Response getProductsByCategory(String categoryId) {

        return given(requestSpec)
                .header("Accept", "application/vnd.api+json")
                .header(
                        "Referer",
                        "https://www.ae.com/us/en/c/men/tops/" + categoryId
                )
                .header("channelType", "WEB")
                .when()
                .get(CATEGORY_ENDPOINT + categoryId)
                .andReturn();
    }

    @Step("Get products by category without Authorization: {categoryId}")
    public Response getProductsByCategoryWithoutAuthorization(
            String categoryId
    ) {

        TestPropertiesConfig configProperties =
                ConfigFactory.create(
                        TestPropertiesConfig.class,
                        System.getProperties()
                );

        return given()
                .baseUri(configProperties.getApiBaseUrl())
                .header("User-Agent", USER_AGENT)
                .header("aecountry", "US")
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header("Accept", "application/vnd.api+json")
                .header(
                        "Referer",
                        "https://www.ae.com/us/en/c/men/tops/"
                                + categoryId
                )
                .header("channelType", "WEB")
                .filter(new AllureRestAssured())
                .when()
                .get(CATEGORY_ENDPOINT + categoryId)
                .andReturn();
    }

    @Step("Get first available SKU from category: {categoryId}")
    public String getFirstSkuFromAvailableProducts(String categoryId) {

        List<String> productIds = getAvailableProductIds(categoryId);

        for (String productId : productIds) {

            Response response = getInventoryByProduct(productId);

            if (response.statusCode() != 200) {
                continue;
            }

            List<String> skuIds = response.jsonPath()
                    .getList(
                            "data.'" + productId + "'.skuId",
                            String.class
                    );

            if (skuIds != null && !skuIds.isEmpty()) {
                return skuIds.get(0);
            }
        }

        throw new IllegalStateException(
                "No available product with SKUs found in category: "
                        + categoryId
        );
    }

    @Step("Get SKUs from two different products in category: {categoryId}")
    public List<String> getSkusFromTwoDifferentProducts(String categoryId) {

        List<String> productIds = getAvailableProductIds(categoryId);
        List<String> skuIds = new ArrayList<>();

        for (String productId : productIds) {

            Response response = getInventoryByProduct(productId);

            if (response.statusCode() != 200) {
                continue;
            }

            List<String> productSkuIds = response.jsonPath()
                    .getList(
                            "data.'" + productId + "'.skuId",
                            String.class
                    );

            if (productSkuIds != null && !productSkuIds.isEmpty()) {

                skuIds.add(productSkuIds.get(0));

                if (skuIds.size() == 2) {
                    return skuIds;
                }
            }
        }

        throw new IllegalStateException(
                "Less than two available products with SKUs found in category: "
                        + categoryId
        );
    }

    @Step("Find product with at least two SKUs in category: {categoryId}")
    public String getProductWithAtLeastTwoSkus(String categoryId) {

        List<String> productIds = getAvailableProductIds(categoryId);

        for (String productId : productIds) {

            Response response = getInventoryByProduct(productId);

            if (response.statusCode() != 200) {
                continue;
            }

            List<String> skuIds = response.jsonPath()
                    .getList(
                            "data.'" + productId + "'.skuId",
                            String.class
                    );

            if (skuIds != null && skuIds.size() >= 2) {
                return productId;
            }
        }

        throw new IllegalStateException(
                "No available product with at least two SKUs found in category: "
                        + categoryId
        );
    }

    @Step("Get available product IDs from category: {categoryId}")
    public List<String> getAvailableProductIds(String categoryId) {

        Response response = getProductsByCategory(categoryId);

        response.then()
                .statusCode(200);

        return response.jsonPath()
                .getList(
                        "included.findAll { "
                                + "it.type == 'product' && "
                                + "it.attributes.isSoldOut == false "
                                + "}.id",
                        String.class
                );
    }
}