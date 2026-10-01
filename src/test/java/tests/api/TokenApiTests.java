package tests.api;

import api.controller.TokenClient;
import io.qameta.allure.Severity;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("API")
public class TokenApiTests {

    @Test
    @Severity(CRITICAL)
    @Tags({
            @Tag("smoke"),
            @Tag("positive")
    })
    @DisplayName("Get guest access token")
    void getGuestTokenTest() {

        Response response =
                TokenClient.getGuestTokenResponse();

        response.then()
                .statusCode(200);

        String accessToken =
                response.jsonPath().getString("access_token");

        assertThat(accessToken)
                .as("Access token must be returned")
                .isNotBlank();

        assertThat(response.jsonPath().getString("token_type"))
                .as("Token type must be Bearer")
                .isEqualTo("Bearer");

        assertThat(response.jsonPath().getString("scope"))
                .as("Guest token must have guest scope")
                .isEqualTo("guest");

        assertThat(response.jsonPath().getInt("expires_in"))
                .as("Guest token lifetime must be greater than zero")
                .isGreaterThan(0);
    }
}