package api.controller;

import io.restassured.response.Response;
import support.TokenManager;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ProductController {

    private static final String BASE_URL = "https://www.ae.com";

    public Response getInventoryByProduct(String productId) {

        Response response =
                given()
                        .baseUri(BASE_URL)

                        .header("Accept", "application/json")
                        .header(
                                "User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                        "Chrome/153.0.0.0 Safari/537.36"
                        )

                        .header("Referer", "https://www.ae.com/")
                        .header("Origin", "https://www.ae.com")

                        .header("aecountry", "US")
                        .header("aelang", "en_US")
                        .header("aesite", "AEO_US")
                        .header(
                                "Authorization",
                                "Bearer " + TokenManager.getToken())

                        .when()
                        .get(
                                "/ugp-api/inventory/v1/groupByProduct/US/"
                                        + productId
                        );

        System.out.println(
                "INVENTORY STATUS: " + response.statusCode()
        );

        System.out.println("INVENTORY RESPONSE:");
        response.prettyPrint();

        return response;
    }

    public List<String> getSkuList(String productId) {

        Response response = getInventoryByProduct(productId);

        response.then().statusCode(200);

        List<String> skuIds = response.jsonPath()
                .getList("data.'" + productId + "'.skuId", String.class);

        System.out.println("SKU LIST: " + skuIds);

        return skuIds;
    }

    public Response getProductsByCategory(String categoryId) {

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .header("Accept", "application/vnd.api+json")
                        .header(
                                "User-Agent",
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                                        "AppleWebKit/537.36 (KHTML, like Gecko) " +
                                        "Chrome/153.0.0.0 Safari/537.36"
                        )
                        .header(
                                "Referer",
                                "https://www.ae.com/us/en/c/men/tops/" + categoryId
                        )
                        .header("aecountry", "US")
                        .header("aelang", "en_US")
                        .header("aesite", "AEO_US")
                        .header("channelType", "WEB")
                        .header(
                                "Authorization",
                                "Bearer " + TokenManager.getToken()
                        )
                        .when()
                        .get("/ugp-api/browse/v1/category/" + categoryId);

        System.out.println(
                "BROWSE STATUS: " + response.statusCode()
        );

        System.out.println("BROWSE RESPONSE:");
        response.prettyPrint();

        return response;
    }

    public String getFirstSkuFromAvailableProducts(String categoryId) {

        List<String> productIds = getAvailableProductIds(categoryId);

        for (String productId : productIds) {

            Response response = getInventoryByProduct(productId);

            if (response.statusCode() != 200) {
                System.out.println(
                        "SKIP PRODUCT " + productId +
                                " — inventory status: " + response.statusCode()
                );
                continue;
            }

            List<String> skuIds = response.jsonPath()
                    .getList(
                            "data.'" + productId + "'.skuId",
                            String.class
                    );

            if (skuIds != null && !skuIds.isEmpty()) {

                String skuId = skuIds.get(0);

                System.out.println(
                        "DYNAMIC PRODUCT ID: " + productId
                );

                System.out.println(
                        "DYNAMIC SKU ID: " + skuId
                );

                return skuId;
            }

            System.out.println(
                    "SKIP PRODUCT " + productId +
                            " — no SKUs found"
            );
        }

        throw new IllegalStateException(
                "No available product with SKUs found in category: "
                        + categoryId
        );
    }

    public List<String> getSkusFromTwoDifferentProducts(String categoryId) {

        List<String> productIds = getAvailableProductIds(categoryId);

        List<String> skuIds = new ArrayList<>();

        for (String productId : productIds) {

            Response response = getInventoryByProduct(productId);

            if (response.statusCode() != 200) {
                System.out.println(
                        "SKIP PRODUCT " + productId +
                                " — inventory status: " + response.statusCode()
                );
                continue;
            }

            List<String> productSkuIds = response.jsonPath()
                    .getList(
                            "data.'" + productId + "'.skuId",
                            String.class
                    );

            if (productSkuIds != null && !productSkuIds.isEmpty()) {

                String skuId = productSkuIds.get(0);

                skuIds.add(skuId);

                System.out.println(
                        "PRODUCT " + productId +
                                " — SELECTED SKU: " + skuId
                );

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

    public String getProductWithAtLeastTwoSkus(String categoryId) {

        List<String> productIds = getAvailableProductIds(categoryId);

        for (String productId : productIds) {

            Response response = getInventoryByProduct(productId);

            if (response.statusCode() != 200) {
                System.out.println(
                        "SKIP PRODUCT " + productId +
                                " — inventory status: " + response.statusCode()
                );
                continue;
            }

            List<String> skuIds = response.jsonPath()
                    .getList(
                            "data.'" + productId + "'.skuId",
                            String.class
                    );

            if (skuIds != null && skuIds.size() >= 2) {

                System.out.println(
                        "PRODUCT WITH AT LEAST TWO SKUS FOUND: " + productId
                );

                System.out.println(
                        "SKU COUNT: " + skuIds.size()
                );

                System.out.println(
                        "SKU LIST: " + skuIds
                );

                return productId;
            }

            System.out.println(
                    "SKIP PRODUCT " + productId +
                            " — less than two SKUs"
            );
        }

        throw new IllegalStateException(
                "No available product with at least two SKUs found in category: "
                        + categoryId
        );
    }

    public List<String> getAvailableProductIds(String categoryId) {

        Response response = getProductsByCategory(categoryId);

        response.then()
                .statusCode(200);

        List<String> productIds = response.jsonPath()
                .getList(
                        "included.findAll { " +
                                "it.type == 'product' && " +
                                "it.attributes.isSoldOut == false " +
                                "}.id",
                        String.class
                );

        System.out.println(
                "AVAILABLE PRODUCT IDS: " + productIds
        );

        return productIds;
    }
}