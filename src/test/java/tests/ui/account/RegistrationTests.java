package tests.ui.account;

import configs.ConfigProvider;
import io.qameta.allure.Severity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.AccountPage;
import pages.HomePage;
import steps.RegistrationSteps;
import tests.ui.BaseTest;
import utils.TestDataGeneratorForCreationAccount;

import java.util.stream.Stream;

import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tags({
        @Tag("UI"),
        @Tag("extended")
})
class RegistrationTests extends BaseTest {

    private static final String UI_BASE_URL =
            ConfigProvider.get().getUiBaseUrl();

    private static final String ACCOUNT_URL =
            "/myaccount/real-rewards/account-summary";

    private static final String POSTAL_CODE = "07008";
    private static final String EMPTY_POSTAL_CODE = "";

    private static final String MONTH_VALUE = "August";
    private static final String DAY_VALUE = "21";

    private static final String ACCOUNT_CREATED_SUCCESS_MESSAGE =
            "Account created!";

    private static final String EMAIL_VALIDATION_MESSAGE =
            "Please enter a valid email address.";

    private static final String EMPTY_PASSWORD_MESSAGE =
            "Please enter your password.";

    private static final String EMPTY_FIRST_NAME_MESSAGE =
            "Please enter your first name.";

    private static final String EMPTY_LAST_NAME_MESSAGE =
            "Please enter your last name.";

    private static final String EMPTY_POSTAL_CODE_MESSAGE =
            "Please enter your zip/postal code.";

    private HomePage homePage;
    private AccountPage accountPage;
    private RegistrationSteps registrationSteps;

    private static Stream<Arguments> emailValidationData() {
        return Stream.of(
                Arguments.of(
                        "Invalid email",
                        "user.gmail.com"
                ),
                Arguments.of(
                        "Empty email",
                        ""
                )
        );
    }

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        accountPage = new AccountPage(driver);
        registrationSteps = new RegistrationSteps(driver);

        homePage.closeBlockingOverlaysIfAvailable();
    }

    private void openRegistrationForm() {
        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickCreateAccountButton();
    }

    @Test
    @Disabled(
            "Automated account creation is blocked by the site's anti-bot protection. " +
                    "Registration form filling works, but submission results in Access Denied."
    )
    @Severity(NORMAL)
    @Tags({
            @Tag("positive"),
            @Tag("defect")
    })
    @DisplayName("Check successful account creation")
    void shouldCreateAccountSuccessfullyTest() {

        String email =
                TestDataGeneratorForCreationAccount.generateEmail();

        String firstName =
                TestDataGeneratorForCreationAccount.generateFirstName();

        String lastName =
                TestDataGeneratorForCreationAccount.generateLastName();

        String password =
                TestDataGeneratorForCreationAccount.generatePassword();

        openRegistrationForm();

        registrationSteps.fillRegistrationForm(
                email,
                firstName,
                lastName,
                password
        );

        accountPage.enterZipCode(POSTAL_CODE);
        accountPage.selectBirthDate(
                MONTH_VALUE,
                DAY_VALUE
        );
        accountPage.scrollToSubmitButton();
        accountPage.acceptTermsAndConditions();

        assertTrue(
                accountPage.submitAccountButtonIsEnabled(),
                "'Create Account' button should be enabled before submission"
        );

        accountPage.clickSubmitButton();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(accountPage.getCurrentUrl())
                .as(
                        "After successful account creation, " +
                                "user should be redirected to account page"
                )
                .isEqualTo(UI_BASE_URL + ACCOUNT_URL);

        softly.assertThat(
                        accountPage.getSuccessfulCreatedAccountText()
                )
                .as(
                        "User should see a success message " +
                                "after account creation"
                )
                .isEqualTo(
                        ACCOUNT_CREATED_SUCCESS_MESSAGE
                );

        softly.assertAll();
    }

    @ParameterizedTest(name = "{index} => {0}")
    @MethodSource("emailValidationData")
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check account creation with invalid email data")
    void accountCreationEmailValidationTest(
            String scenario,
            String email
    ) {

        String firstName =
                TestDataGeneratorForCreationAccount.generateFirstName();

        String lastName =
                TestDataGeneratorForCreationAccount.generateLastName();

        String password =
                TestDataGeneratorForCreationAccount.generatePassword();

        openRegistrationForm();

        registrationSteps.fillRegistrationForm(
                email,
                firstName,
                lastName,
                password
        );

        accountPage.enterZipCode(POSTAL_CODE);
        accountPage.selectBirthDate(
                MONTH_VALUE,
                DAY_VALUE
        );
        accountPage.scrollToSubmitButton();
        accountPage.acceptTermsAndConditions();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(
                        accountPage.submitAccountButtonIsEnabled()
                )
                .as(
                        "'Create Account' button should be " +
                                "disabled for scenario: " + scenario
                )
                .isFalse();

        softly.assertThat(
                        accountPage.isEmailValidationErrorDisplayed()
                )
                .as(
                        "Email validation error should be displayed " +
                                "for scenario: " + scenario
                )
                .isTrue();

        softly.assertThat(
                        accountPage.getEmailValidationErrorMessage()
                )
                .as(
                        "Email validation error message should be correct " +
                                "for scenario: " + scenario
                )
                .isEqualTo(EMAIL_VALIDATION_MESSAGE);

        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName(
            "Check account creation with empty 'First Name' " +
                    "and empty 'Last Name'"
    )
    void createAccountWithEmptyNameFieldsTest() {

        String email =
                TestDataGeneratorForCreationAccount.generateEmail();

        String emptyFirstName = "";
        String emptyLastName = "";

        String password =
                TestDataGeneratorForCreationAccount.generatePassword();

        openRegistrationForm();

        registrationSteps.fillRegistrationForm(
                email,
                emptyFirstName,
                emptyLastName,
                password
        );

        accountPage.enterZipCode(POSTAL_CODE);
        accountPage.selectBirthDate(
                MONTH_VALUE,
                DAY_VALUE
        );
        accountPage.scrollToSubmitButton();
        accountPage.acceptTermsAndConditions();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(
                        accountPage.submitAccountButtonIsEnabled()
                )
                .as(
                        "'Create Account' button should be " +
                                "disabled with empty name fields"
                )
                .isFalse();

        softly.assertThat(
                        accountPage.isEmptyFirstNameErrorDisplayed()
                )
                .as(
                        "Error message for empty First Name " +
                                "should be visible"
                )
                .isTrue();

        softly.assertThat(
                        accountPage.getErrorEmptyFirstNameMessage()
                )
                .as(
                        "Error message for empty First Name " +
                                "should match expected text"
                )
                .isEqualTo(EMPTY_FIRST_NAME_MESSAGE);

        softly.assertThat(
                        accountPage.isEmptyLastNameErrorDisplayed()
                )
                .as(
                        "Error message for empty Last Name " +
                                "should be visible"
                )
                .isTrue();

        softly.assertThat(
                        accountPage.getErrorEmptyLastNameMessage()
                )
                .as(
                        "Error message for empty Last Name " +
                                "should match expected text"
                )
                .isEqualTo(EMPTY_LAST_NAME_MESSAGE);

        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check account creation with empty password")
    void createAccountWithEmptyPasswordTest() {

        String email =
                TestDataGeneratorForCreationAccount.generateEmail();

        String firstName =
                TestDataGeneratorForCreationAccount.generateFirstName();

        String lastName =
                TestDataGeneratorForCreationAccount.generateLastName();

        String emptyPassword = "";

        openRegistrationForm();

        registrationSteps.fillRegistrationForm(
                email,
                firstName,
                lastName,
                emptyPassword
        );

        accountPage.enterZipCode(POSTAL_CODE);
        accountPage.selectBirthDate(
                MONTH_VALUE,
                DAY_VALUE
        );
        accountPage.scrollToSubmitButton();
        accountPage.acceptTermsAndConditions();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(
                        accountPage.submitAccountButtonIsEnabled()
                )
                .as(
                        "Create Account button should be " +
                                "disabled when password is empty"
                )
                .isFalse();

        softly.assertThat(
                        accountPage.isEmptyPasswordErrorDisplayed()
                )
                .as(
                        "Password error message should be visible"
                )
                .isTrue();

        softly.assertThat(
                        accountPage.getErrorEmptyPasswordMessage()
                )
                .as(
                        "Password error message text " +
                                "should match expected"
                )
                .isEqualTo(EMPTY_PASSWORD_MESSAGE);

        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check account creation with empty 'Zip Code'")
    void createAccountWithEmptyZipCodeTest() {

        String email =
                TestDataGeneratorForCreationAccount.generateEmail();

        String firstName =
                TestDataGeneratorForCreationAccount.generateFirstName();

        String lastName =
                TestDataGeneratorForCreationAccount.generateLastName();

        String password =
                TestDataGeneratorForCreationAccount.generatePassword();

        openRegistrationForm();

        registrationSteps.fillRegistrationForm(
                email,
                firstName,
                lastName,
                password
        );

        accountPage.enterZipCode(EMPTY_POSTAL_CODE);
        accountPage.selectBirthDate(
                MONTH_VALUE,
                DAY_VALUE
        );
        accountPage.scrollToSubmitButton();
        accountPage.acceptTermsAndConditions();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(
                        accountPage.submitAccountButtonIsEnabled()
                )
                .as(
                        "Submit button must be disabled " +
                                "when 'Zip Code' is empty"
                )
                .isFalse();

        softly.assertThat(
                        accountPage.isEmptyZipCodeErrorDisplayed()
                )
                .as(
                        "Error message for empty 'Zip Code' " +
                                "must be visible"
                )
                .isTrue();

        softly.assertThat(
                        accountPage.getErrorEmptyZipCodeMessage()
                )
                .as(
                        "Error message for empty 'Zip Code' " +
                                "must match expected text"
                )
                .isEqualTo(EMPTY_POSTAL_CODE_MESSAGE);

        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName(
            "Check account creation with empty 'Birthday' field " +
                    "and the checkbox 'I accept' unchecked"
    )
    void createAccountWithEmptyBirthDateTest() {

        String email =
                TestDataGeneratorForCreationAccount.generateEmail();

        String firstName =
                TestDataGeneratorForCreationAccount.generateFirstName();

        String lastName =
                TestDataGeneratorForCreationAccount.generateLastName();

        String password =
                TestDataGeneratorForCreationAccount.generatePassword();

        openRegistrationForm();

        registrationSteps.fillRegistrationForm(
                email,
                firstName,
                lastName,
                password
        );

        accountPage.enterZipCode(POSTAL_CODE);
        accountPage.scrollToSubmitButton();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(
                        accountPage.isTermsCheckboxSelected()
                )
                .as(
                        "'I accept' checkbox should be " +
                                "unchecked by default"
                )
                .isFalse();

        softly.assertThat(
                        accountPage.submitAccountButtonIsEnabled()
                )
                .as(
                        "Submit button should be disabled when " +
                                "birth date and checkbox are not filled"
                )
                .isFalse();

        softly.assertAll();
    }
}