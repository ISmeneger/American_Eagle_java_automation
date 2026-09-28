package api.controller;

import configs.TestPropertiesConfig;
import dto.AddItemRequest;
import dto.BagResponse;
import dto.UpdateItemRequest;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import support.TokenManager;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class BagController {
    private final RequestSpecification requestSpecification = given();

    private static final String ITEMS_ENDPOINT = "/ugp-api/bag/v1/items";
    private static final String BAG_ENDPOINT = "/ugp-api/bag/v1";

    public BagController() {

        TestPropertiesConfig configProperties =
                ConfigFactory.create(
                        TestPropertiesConfig.class,
                        System.getProperties()
                );

        requestSpecification
                .accept(JSON)
                .contentType(JSON)
                .baseUri(configProperties.getApiBaseUrl())
                .header("aesite", "AEO_US")
                .header("Aecountry", "US")
                .header("Aelang", "en_US")
                .header("Authorization", "Bearer " + TokenManager.getToken())
                .filter(new AllureRestAssured());
    }

    @Step("Add item to bag")
    public Response addItem(String skuId, int quantity) {

        AddItemRequest.Item item = new AddItemRequest.Item(skuId, quantity);
        AddItemRequest request = new AddItemRequest(List.of(item));

        Response response = given(this.requestSpecification)
                .body(request)
                .when()
                .post(ITEMS_ENDPOINT)
                .andReturn();

        System.out.println("ADD ITEM SKU: " + skuId);
        System.out.println("ADD ITEM STATUS: " + response.statusCode());
        System.out.println("ADD ITEM RESPONSE:");
        System.out.println(response.asPrettyString());

        return response;
    }

    @Step("Get bag")
    public BagResponse getBag() {

        Response response = given(this.requestSpecification)
                .queryParam("couponErrorBehavior", "cart")
                .queryParam("inventoryCheck", true)
                .when()
                .get(BAG_ENDPOINT)
                .andReturn();

        System.out.println("GET BAG STATUS: " + response.statusCode());
        System.out.println("GET BAG RESPONSE:");
        System.out.println(response.getBody().asPrettyString());

        response.then()
                .statusCode(200);

        return response.as(BagResponse.class);
    }

    @Step("Update item in bag")
    public Response updateItem(String skuId, int quantity, String itemId) {

        UpdateItemRequest.Item item = new UpdateItemRequest.Item(skuId, quantity, itemId);
        UpdateItemRequest request = new UpdateItemRequest(List.of(item));

        return given(this.requestSpecification)
                .body(request)
                .when()
                .patch(ITEMS_ENDPOINT)
                .andReturn();
    }

    @Step("Delete item in bag")
    public Response deleteItem(String itemId) {

        return given(this.requestSpecification)
                .queryParam("itemIds", itemId)
                .when()
                .delete(ITEMS_ENDPOINT)
                .andReturn();
    }
}
