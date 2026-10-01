package tests.ui.account;

import configs.TestPropertiesConfig;
import io.qameta.allure.Severity;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AccountPage;
import pages.HomePage;
import tests.ui.BaseTest;

import java.time.Duration;

import static constants.CommonConstants.BASE_URL;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tags({
        @Tag("UI"),
        @Tag("extended")
})
class SignInTests extends BaseTest {

    private HomePage homePage;
    private AccountPage accountPage;

    private final TestPropertiesConfig config =
            ConfigFactory.create(
                    TestPropertiesConfig.class,
                    System.getProperties()
            );

    private static final String ACCOUNT_URL =
            "/myaccount/real-rewards/account-summary";

    private static final String ACCOUNT_SUCCESSFUL_ENTERED_MESSAGE =
            "Ilya's Account";

    private static final String EMAIL_VALIDATION_MESSAGE =
            "Please enter a valid email address.";

    private static final String WRONG_PASSWORD_MESSAGE =
            "Please enter a password that contains 8-25 characters " +
                    "with at least one letter and one number.";

    private static final String EMPTY_PASSWORD_MESSAGE =
            "Please enter your password.";

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        accountPage = new AccountPage(driver);

        homePage.closeBlockingOverlaysIfAvailable();
    }

    @Test
    @Disabled(
            "Automated sign-in is blocked by the site's anti-bot protection. " +
                    "Manual sign-in works successfully."
    )
    @Severity(NORMAL)
    @Tags({
            @Tag("positive"),
            @Tag("defect")
    })
    @DisplayName("Check successful sign in")
    void signInPageTest() {

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(config.getEmail());
        accountPage.continueButtonClick();

        accountPage.selectPasswordSignInMethod();
        accountPage.inputPasswordField(config.getPassword());
        accountPage.submitSignInButtonClick();

        new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        ).until(
                ExpectedConditions.urlContains(
                        ACCOUNT_URL
                )
        );

        assertEquals(
                BASE_URL + ACCOUNT_URL,
                driver.getCurrentUrl(),
                "After successful sign in, user should be " +
                        "redirected to account page"
        );

        assertThat(
                accountPage.getSuccessfulEnteredAccountText()
        )
                .as(
                        "Account page should display " +
                                "the signed-in user's account"
                )
                .isEqualTo(
                        ACCOUNT_SUCCESSFUL_ENTERED_MESSAGE
                );
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check sign in with invalid email")
    void invalidEmailSignInPageTest() {

        String invalidEmail = "invalid-email";

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(invalidEmail);
        accountPage.continueButtonClick();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(
                        accountPage.isSignInInvalidEmailErrorDisplayed()
                )
                .as(
                        "Email validation message should " +
                                "be displayed"
                )
                .isTrue();

        softly.assertThat(
                        accountPage.getSignInInvalidEmailErrorMessage()
                )
                .as(
                        "Email validation message should " +
                                "match expected text"
                )
                .isEqualTo(EMAIL_VALIDATION_MESSAGE);

        softly.assertAll();
    }

    @Test
    @Disabled(
            "Password validation cannot be reached reliably " +
                    "because automated sign-in is blocked by anti-bot protection."
    )
    @Severity(NORMAL)
    @Tags({
            @Tag("negative"),
            @Tag("defect")
    })
    @DisplayName("Check sign in with invalid password")
    void invalidPasswordSignInPageTest() {

        String invalidPassword = "123456789";

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(config.getEmail());
        accountPage.continueButtonClick();

        accountPage.selectPasswordSignInMethod();
        accountPage.inputPasswordField(invalidPassword);
        accountPage.submitSignInButtonClick();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(
                        accountPage.getErrorAccountInvalidPasswordMessageIsDisplayed()
                )
                .as(
                        "Password validation message should " +
                                "be displayed"
                )
                .isTrue();

        softly.assertThat(
                        accountPage.getErrorAccountInvalidPasswordMessage()
                )
                .as(
                        "Password validation message should " +
                                "match expected text"
                )
                .isEqualTo(WRONG_PASSWORD_MESSAGE);

        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check sign in with empty email")
    void emptyEmailSignInPageTest() {

        String emptyEmail = "";

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(emptyEmail);
        accountPage.continueButtonClick();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(
                        accountPage.isSignInInvalidEmailErrorDisplayed()
                )
                .as(
                        "Email validation message should be " +
                                "displayed when email is empty"
                )
                .isTrue();

        softly.assertThat(
                        accountPage.getSignInInvalidEmailErrorMessage()
                )
                .as(
                        "Empty email validation message should " +
                                "match expected text"
                )
                .isEqualTo(EMAIL_VALIDATION_MESSAGE);

        softly.assertAll();
    }

    @Test
    @Disabled(
            "Password validation cannot be reached reliably " +
                    "because automated sign-in is blocked by anti-bot protection."
    )
    @Severity(NORMAL)
    @Tags({
            @Tag("negative"),
            @Tag("defect")
    })
    @DisplayName("Check sign in with empty password")
    void emptyPasswordSignInPageTest() {

        String emptyPassword = "";

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(config.getEmail());
        accountPage.continueButtonClick();

        accountPage.selectPasswordSignInMethod();
        accountPage.inputPasswordField(emptyPassword);
        accountPage.submitSignInButtonClick();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(
                        accountPage.getErrorEmptyPasswordMessageIsDisplayed()
                )
                .as(
                        "Password validation message should be " +
                                "displayed when password is empty"
                )
                .isTrue();

        softly.assertThat(
                        accountPage.getErrorEmptyPasswordMessage()
                )
                .as(
                        "Empty password validation message should " +
                                "match expected text"
                )
                .isEqualTo(EMPTY_PASSWORD_MESSAGE);

        softly.assertAll();
    }

    @Test
    @Disabled(
            "Password validation cannot be reached reliably " +
                    "because automated sign-in is blocked by anti-bot protection."
    )
    @Severity(NORMAL)
    @Tags({
            @Tag("negative"),
            @Tag("defect")
    })
    @DisplayName("Check sign in with short password")
    void shortPasswordSignInPageTest() {

        String shortPassword = "12345";

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(config.getEmail());
        accountPage.continueButtonClick();

        accountPage.selectPasswordSignInMethod();
        accountPage.inputPasswordField(shortPassword);
        accountPage.submitSignInButtonClick();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(
                        accountPage.getErrorAccountInvalidPasswordMessageIsDisplayed()
                )
                .as(
                        "Password validation message should be " +
                                "displayed for a short password"
                )
                .isTrue();

        softly.assertThat(
                        accountPage.getErrorAccountInvalidPasswordMessage()
                )
                .as(
                        "Password validation message should " +
                                "match expected text"
                )
                .isEqualTo(WRONG_PASSWORD_MESSAGE);

        softly.assertAll();
    }

    @Test
    @Disabled(
            "Password validation cannot be reached reliably " +
                    "because automated sign-in is blocked by anti-bot protection."
    )
    @Severity(NORMAL)
    @Tags({
            @Tag("negative"),
            @Tag("defect")
    })
    @DisplayName("Check sign in with long password")
    void longPasswordSignInPageTest() {

        String longPassword =
                "12345qwerrttfgfhdhfhdfhfdhdfhdfh";

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(config.getEmail());
        accountPage.continueButtonClick();

        accountPage.selectPasswordSignInMethod();
        accountPage.inputPasswordField(longPassword);
        accountPage.submitSignInButtonClick();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(
                        accountPage.getErrorAccountInvalidPasswordMessageIsDisplayed()
                )
                .as(
                        "Password validation message should be " +
                                "displayed for a long password"
                )
                .isTrue();

        softly.assertThat(
                        accountPage.getErrorAccountInvalidPasswordMessage()
                )
                .as(
                        "Password validation message should " +
                                "match expected text"
                )
                .isEqualTo(WRONG_PASSWORD_MESSAGE);

        softly.assertAll();
    }
}