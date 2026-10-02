package api.controller;

import configs.TestPropertiesConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.NotImplementedException;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.URLENC;

public class TokenClient {

    private static final String TOKEN_ENDPOINT =
            "/ugp-api/auth/oauth/v5/token";

    private static final TestPropertiesConfig configProperties =
            ConfigFactory.create(
                    TestPropertiesConfig.class,
                    System.getProperties()
            );

    private static RequestSpecification guestAuthSpec() {
        return given()
                .baseUri(configProperties.getApiBaseUrl())
                .accept(JSON)
                .contentType(URLENC)
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header("aecountry", "US")
                .header(
                        "Authorization",
                        configProperties.getGuestHeaderAuth()
                )
                .filter(new AllureRestAssured());
    }

    @Step("Get guest token response")
    public static Response getGuestTokenResponse() {
        return guestAuthSpec()
                .formParam("grant_type", "client_credentials")
                .when()
                .post(TOKEN_ENDPOINT)
                .andReturn();
    }

    @Step("Get guest token")
    public static String getGuestToken() {

        Response response = getGuestTokenResponse();

        response.then()
                .statusCode(200);

        return response.jsonPath()
                .getString("access_token");
    }

    @Step("Get authorized token")
    public static String getAuthorizedToken() {
        throw new NotImplementedException(
                "Not yet implemented!"
        );
    }
}