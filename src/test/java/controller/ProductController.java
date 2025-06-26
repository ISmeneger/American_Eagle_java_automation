package controller;

import configs.TestPropertiesConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Item;
import org.aeonbits.owner.ConfigFactory;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ProductController {
    RequestSpecification requestSpecification = given();
    TestPropertiesConfig configProperties = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    public ProductController() {
        requestSpecification.baseUri(configProperties.getApiBaseUrl());
        requestSpecification.accept("application/json");
        requestSpecification.contentType("application/json");
        requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Put product in bug")
    public Response createProduct(Item product) {
        requestSpecification.body(product);
        return given(requestSpecification).when().post("v1/items");
    }
}
