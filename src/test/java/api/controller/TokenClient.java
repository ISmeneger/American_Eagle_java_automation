
package api.controller;

import configs.TestPropertiesConfig;
import io.qameta.allure.Step;
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

    /**
     * Reads guest authorization credentials from
     * the AE_GUEST_AUTH environment variable.
     *
     * Credentials are never printed or included
     * in JVM system properties.
     */
    private static String getGuestAuthorization() {

        String authorization =
                System.getenv("AE_GUEST_AUTH");

        if (authorization == null || authorization.isBlank()) {
            throw new IllegalStateException(
                    "AE_GUEST_AUTH environment variable is not set"
            );
        }

        if (!authorization.startsWith("Basic ")) {
            throw new IllegalStateException(
                    "AE_GUEST_AUTH must start with 'Basic '"
            );
        }

        return authorization;
    }

    /**
     * Base request specification for guest token API.
     */
    private static RequestSpecification guestBaseSpec() {

        return given()
                .baseUri(configProperties.getApiBaseUrl())
                .accept(JSON)
                .contentType(URLENC)
                .header("aelang", "en_US")
                .header("aesite", "AEO_US")
                .header("aecountry", "US");
    }

    /**
     * Request specification with guest authorization.
     */
    private static RequestSpecification guestAuthSpec() {

        return given()
                .spec(guestBaseSpec())
                .header(
                        "Authorization",
                        getGuestAuthorization()
                );
    }

    @Step("Get guest token response")
    public static Response getGuestTokenResponse() {

        return guestAuthSpec()
                .formParam(
                        "grant_type",
                        "client_credentials"
                )
                .when()
                .post(TOKEN_ENDPOINT)
                .andReturn();
    }

    @Step("Get guest token response without Authorization")
    public static Response getGuestTokenResponseWithoutAuthorization() {

        return guestBaseSpec()
                .formParam(
                        "grant_type",
                        "client_credentials"
                )
                .when()
                .post(TOKEN_ENDPOINT)
                .andReturn();
    }

    @Step("Get guest token")
    public static String getGuestToken() {

        Response response =
                getGuestTokenResponse();

        response.then()
                .statusCode(200);

        String accessToken =
                response.jsonPath()
                        .getString("access_token");

        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalStateException(
                    "Guest access token was not returned by API"
            );
        }

        return accessToken;
    }

    @Step("Get authorized token")
    public static String getAuthorizedToken() {

        throw new NotImplementedException(
                "Not yet implemented!"
        );
    }
}