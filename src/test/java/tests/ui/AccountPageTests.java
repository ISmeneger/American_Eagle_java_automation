package tests.ui;

import configs.TestPropertiesConfig;
import io.qameta.allure.Severity;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AccountPage;
import pages.HomePage;
import steps.CreateNewAccountPage;
import utils.TestDataGeneratorForCreationAccount;

import java.time.Duration;

import static constants.CommonConstants.*;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tags({@Tag("UI"), @Tag("extended")})
class AccountPageTests extends BaseTest {
    HomePage homePage;
    AccountPage accountPage;
    CreateNewAccountPage createNewAccountPage;
    TestPropertiesConfig config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    private static final String ACCOUNT_URL = "/myaccount/real-rewards/account-summary";

    private static final String POSTAL_CODE = "07008";
    private static final String EMPTY_POSTAL_CODE = "";
    private static final String EMPTY_POSTAL_CODE_MESSAGE = "Please enter your zip/postal code.";
    private static final String MONTH_VALUE = "8";
    private static final String DAY_VALUE = "21";
    private static final String ACCOUNT_CREATED_SUCCESSFUL_MESSAGE = "Account created!";
    private static final String ACCOUNT_SUCCESSFUL_ENTERED_MESSAGE = "Ilya's Account";
    private static final String PROBLEM_REPORT = "Hold up, there's a problem.";
    private static final String WRONG_EMAIL_MESSAGE = "Please enter a valid email address.";
    private static final String EMPTY_EMAIL_MESSAGE = "Please enter your email address.";
    private static final String WRONG_PASSWORD_MESSAGE = "Please enter a password that contains 8-25 characters with at least one letter and one number.";
    private static final String EMPTY_PASSWORD_MESSAGE = "Please enter your password.";
    private static final String EMPTY_FIRST_NAME_MESSAGE = "Please enter your first name.";
    private static final String EMPTY_LAST_NAME_MESSAGE = "Please enter your last name.";

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
    }

    @Test
    @Severity(NORMAL)
    @Tags({@Tag("positive"), @Tag("defect")})
    @DisplayName("Check successful account creation")
    void shouldCreateAccountSuccessfullyTest() {
        String email = TestDataGeneratorForCreationAccount.generateEmail();
        String firstName = TestDataGeneratorForCreationAccount.generateFirstName();
        String lastName = TestDataGeneratorForCreationAccount.generateLastName();
        String password = TestDataGeneratorForCreationAccount.generatePassword();

        accountPage = new AccountPage(driver);
        createNewAccountPage = new CreateNewAccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickCreateAccountButton();

        createNewAccountPage.createNewAccount(email, firstName, lastName, password);

        accountPage.enterZipCode(POSTAL_CODE);
        accountPage.selectBirthDate(MONTH_VALUE, DAY_VALUE);
        accountPage.scrollToSubmitButton();
        accountPage.acceptTermsAndConditions();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("'Create Account' button should be enabled before submission").isTrue();
        softly.assertAll();

        accountPage.clickSubmitButton();

        softly.assertThat(accountPage.getCurrentUrl())
                .as("After successful account creation, user should be redirected to account page")
                .isEqualTo(BASE_URL + ACCOUNT_URL);

        softly.assertThat(accountPage.getSuccessfulCreatedAccountText())
                .as("User should see a success message after account creation")
                .isEqualTo(ACCOUNT_CREATED_SUCCESSFUL_MESSAGE);
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check account creation with invalid email")
    void createAccountPageWithInvalidEmailTest() {
        String invalidEmail = "user.gmail.com" ;
        String firstName = TestDataGeneratorForCreationAccount.generateFirstName();
        String lastName = TestDataGeneratorForCreationAccount.generateLastName();
        String password = TestDataGeneratorForCreationAccount.generatePassword();

        accountPage = new AccountPage(driver);
        createNewAccountPage = new CreateNewAccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickCreateAccountButton();

        createNewAccountPage.createNewAccount(invalidEmail, firstName, lastName, password);

        accountPage.enterZipCode(POSTAL_CODE);
        accountPage.selectBirthDate(MONTH_VALUE, DAY_VALUE);
        accountPage.scrollToSubmitButton();
        accountPage.acceptTermsAndConditions();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("'Create Account' button should be disabled when email is invalid")
                .isFalse();

        softly.assertThat(accountPage.getErrorEmailMessageIsDisplayed())
                .as("Email error message should be displayed")
                .isTrue();

        softly.assertThat(accountPage.getErrorEmailMessage())
                .as("Error message for invalid email should appear")
                .isEqualTo(WRONG_EMAIL_MESSAGE);
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check account creation with empty email")
    void createAccountPageWithEmptyEmailTest(){
        String emptyEmail = "" ;
        String firstName = TestDataGeneratorForCreationAccount.generateFirstName();
        String lastName = TestDataGeneratorForCreationAccount.generateLastName();
        String password = TestDataGeneratorForCreationAccount.generatePassword();

        accountPage = new AccountPage(driver);
        createNewAccountPage = new CreateNewAccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickCreateAccountButton();

        createNewAccountPage.createNewAccount(emptyEmail, firstName, lastName, password);

        accountPage.enterZipCode(POSTAL_CODE);
        accountPage.selectBirthDate(MONTH_VALUE, DAY_VALUE);
        accountPage.scrollToSubmitButton();
        accountPage.acceptTermsAndConditions();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("'Create Account' button should be disabled when email field is empty")
                .isFalse();

        softly.assertThat(accountPage.getErrorEmptyEmailMessageIsDisplayed())
                .as("Email error message should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorEmptyEmailMessage())
                .as("Error message for empty email field should be correct")
                .isEqualTo(EMPTY_EMAIL_MESSAGE);
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check account creation with empty 'First Name' and empty 'Last Name'")
    void createAccountPageWithEmptyNamesFieldsTest(){
        String email = TestDataGeneratorForCreationAccount.generateEmail();
        String emptyFirstName = "";
        String emptyLastName = "";
        String password = TestDataGeneratorForCreationAccount.generatePassword();

        accountPage = new AccountPage(driver);
        createNewAccountPage = new CreateNewAccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickCreateAccountButton();

        createNewAccountPage.createNewAccount(email, emptyFirstName, emptyLastName, password);

        accountPage.enterZipCode(POSTAL_CODE);
        accountPage.selectBirthDate(MONTH_VALUE, DAY_VALUE);
        accountPage.scrollToSubmitButton();
        accountPage.acceptTermsAndConditions();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("'Create Account' button should be disabled with empty name fields")
                .isFalse();

        softly.assertThat(accountPage.getErrorEmptyFirstNameMessageIsDisplayed())
                .as("Error message for empty First Name should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorEmptyFirstNameMessage())
                .as("Error message for empty First Name should match expected text")
                .isEqualTo(EMPTY_FIRST_NAME_MESSAGE);

        softly.assertThat(accountPage.getErrorEmptyLastNameMessageIsDisplayed())
                .as("Error message for empty Last Name should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorEmptyLastNameMessage())
                .as("Error message for empty Last Name should match expected text")
                .isEqualTo(EMPTY_LAST_NAME_MESSAGE);
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check account creation with empty password")
    void createAccountPageWithEmptyPasswordTest(){
        String email = TestDataGeneratorForCreationAccount.generateEmail();
        String firstName = TestDataGeneratorForCreationAccount.generateFirstName();
        String lastName = TestDataGeneratorForCreationAccount.generateLastName();
        String emptyPassword = "";

        accountPage = new AccountPage(driver);
        createNewAccountPage = new CreateNewAccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickCreateAccountButton();

        createNewAccountPage.createNewAccount(email, firstName, lastName, emptyPassword);

        accountPage.enterZipCode(POSTAL_CODE);
        accountPage.selectBirthDate(MONTH_VALUE, DAY_VALUE);
        accountPage.scrollToSubmitButton();
        accountPage.acceptTermsAndConditions();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("Create Account button should be disabled when password is empty")
                .isFalse();

        softly.assertThat(accountPage.getErrorEmptyPasswordMessageIsDisplayed())
                .as("Password error message should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorEmptyPasswordMessage())
                .as("Password error message text should match expected")
                .isEqualTo(EMPTY_PASSWORD_MESSAGE);
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check account creation with empty 'Zip Code'")
    void createAccountPageWithEmptyZipCodeTest(){
        String email = TestDataGeneratorForCreationAccount.generateEmail();
        String firstName = TestDataGeneratorForCreationAccount.generateFirstName();
        String lastName = TestDataGeneratorForCreationAccount.generateLastName();
        String password = TestDataGeneratorForCreationAccount.generatePassword();

        accountPage = new AccountPage(driver);
        createNewAccountPage = new CreateNewAccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickCreateAccountButton();

        createNewAccountPage.createNewAccount(email, firstName, lastName, password);

        accountPage.enterZipCode(EMPTY_POSTAL_CODE);
        accountPage.selectBirthDate(MONTH_VALUE, DAY_VALUE);
        accountPage.scrollToSubmitButton();
        accountPage.acceptTermsAndConditions();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("Submit button must be disabled when 'Zip Code' is empty")
                .isFalse();

        softly.assertThat(accountPage.getErrorEmptyZipCOdeMessageIsDisplayed())
                .as("Error message for empty 'Zip Code' must be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorEmptyZipCOdeMessage())
                .as("Error message for empty 'Zip Code' must match expected text")
                .isEqualTo(EMPTY_POSTAL_CODE_MESSAGE);
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check account creation with empty 'Birthday' field and the checkbox 'I accept' unchecked")
    void createAccountPageWithEmptyBirthDateTest(){
        String email = TestDataGeneratorForCreationAccount.generateEmail();
        String firstName = TestDataGeneratorForCreationAccount.generateFirstName();
        String lastName = TestDataGeneratorForCreationAccount.generateLastName();
        String password = TestDataGeneratorForCreationAccount.generatePassword();

        accountPage = new AccountPage(driver);
        createNewAccountPage = new CreateNewAccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickCreateAccountButton();

        createNewAccountPage.createNewAccount(email, firstName, lastName, password);
        accountPage.enterZipCode(POSTAL_CODE);
        accountPage.scrollToSubmitButton();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.isTermsCheckboxSelected())
                .as("'I accept' checkbox should be unchecked by default")
                .isFalse();

        softly.assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("Submit button should be disabled when birth date and checkbox are not filled")
                .isFalse();
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tags({@Tag("positive"), @Tag("defect")})
    @DisplayName("Check successful 'Sign In' account")
    void signInPageTest() {
        accountPage = new AccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(config.getEmail());
        accountPage.inputPasswordField(config.getPassword());
        accountPage.submitSignInButtonClick();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(BASE_URL + ACCOUNT_URL));

        assertEquals(BASE_URL + ACCOUNT_URL, accountPage.getCurrentUrl(),
                "User should be redirected to account page after successful login");

        assertThat(accountPage.getSuccessfulEnteredAccountText())
                .as("The successful login message should match expected")
                .isEqualTo(ACCOUNT_SUCCESSFUL_ENTERED_MESSAGE);
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check 'Sign In' with invalid email input")
    void invalidEmailSignInPageTest() {
        String invalidEmail = "user.gmail.com";

        accountPage = new AccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(invalidEmail);
        accountPage.inputPasswordField(config.getPassword());
        accountPage.submitSignInButtonClick();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.getLoginWarningError())
                .as("Login warning message should match expected")
                .isEqualTo(PROBLEM_REPORT);

        softly.assertThat(accountPage.getLoginWarningErrorIsDisplayed())
                .as("Login warning message should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorEmailMessage())
                .as("Email validation error message should match expected")
                .isEqualTo(WRONG_EMAIL_MESSAGE);

        softly.assertThat(accountPage.getErrorEmailMessageIsDisplayed())
                .as("Email validation error should be visible")
                .isTrue();
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check 'Sign In' with invalid password")
    void invalidPasswordSignInPageTest() {
        String invalidPassword = "123456789";

        accountPage = new AccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(config.getEmail());
        accountPage.inputPasswordField(invalidPassword);
        accountPage.submitSignInButtonClick();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.getLoginWarningErrorIsDisplayed())
                .as("General login error message should be visible")
                .isTrue();

        softly.assertThat(accountPage.getLoginWarningError())
                .as("Login warning message should match expected").isEqualTo(PROBLEM_REPORT);

        softly.assertThat(accountPage.getErrorAccountInvalidPasswordMessageIsDisplayed() )
                .as("Password validation error should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorAccountInvalidPasswordMessage())
                .as("Password validation error message should match expected")
                .isEqualTo(WRONG_PASSWORD_MESSAGE);
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check 'Sign In' with empty email input")
    void emptyEmailSignInPageTest() {
        String emptyEmail = "";

        accountPage = new AccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(emptyEmail);
        accountPage.inputPasswordField(config.getPassword());
        accountPage.submitSignInButtonClick();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.getLoginWarningErrorIsDisplayed())
                .as("General login warning message should be visible")
                .isTrue();

        softly.assertThat(accountPage.getLoginWarningError())
                .as("Login warning message should match expected")
                .isEqualTo(PROBLEM_REPORT);

        softly.assertThat(accountPage.getErrorEmptyEmailMessageIsDisplayed())
                .as("Validation message for empty email should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorEmptyEmailMessage())
                .as("Empty email validation message should match expected")
                .isEqualTo(EMPTY_EMAIL_MESSAGE);
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check 'Sign In' with empty password input")
    void emptyPasswordSignInPageTest() {
        String emptyPassword = "";

        accountPage = new AccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(config.getEmail());
        accountPage.inputPasswordField(emptyPassword);
        accountPage.submitSignInButtonClick();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.getLoginWarningErrorIsDisplayed())
                .as("General login warning message should be visible")
                .isTrue();

        softly.assertThat(accountPage.getLoginWarningError())
                .as("Login warning message should match expected")
                .isEqualTo(PROBLEM_REPORT);

        softly.assertThat(accountPage.getErrorEmptyPasswordMessageIsDisplayed())
                .as("Validation message for empty password should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorEmptyPasswordMessage())
                .as("Empty password validation message should match expected")
                .isEqualTo(EMPTY_PASSWORD_MESSAGE);
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check 'Sign In' with empty email and empty password input")
    void emptyEmailAndEmptyPasswordSignInPageTest() {
        String emptyEmail = "";
        String emptyPassword = "";

        accountPage = new AccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(emptyEmail);
        accountPage.inputPasswordField(emptyPassword);
        accountPage.submitSignInButtonClick();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.getLoginWarningErrorIsDisplayed())
                .as("General login warning message should be visible")
                .isTrue();

        softly.assertThat(accountPage.getLoginWarningError())
                .as("Login warning message text should match expected")
                .isEqualTo(PROBLEM_REPORT);

        softly.assertThat(accountPage.getErrorEmptyEmailMessageIsDisplayed())
                .as("Email empty validation message should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorEmptyEmailMessage())
                .as("Empty email validation message should match expected")
                .isEqualTo(EMPTY_EMAIL_MESSAGE);

        softly.assertThat(accountPage.getErrorEmptyPasswordMessageIsDisplayed())
                .as("Password empty validation message should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorEmptyPasswordMessage())
                .as("Empty password validation message should match expected")
                .isEqualTo(EMPTY_PASSWORD_MESSAGE);
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check 'Sign In' with password shorter than 8 characters")
    void shortPasswordSignInPageTest() {
        String shortPassword = "12345";

        accountPage = new AccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(config.getEmail());
        accountPage.inputPasswordField(shortPassword);
        accountPage.submitSignInButtonClick();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.getLoginWarningErrorIsDisplayed())
                .as("Login warning should be displayed for invalid credentials")
                .isTrue();

        softly.assertThat(accountPage.getLoginWarningError())
                .as("General login warning text should match expected")
                .isEqualTo(PROBLEM_REPORT);

        softly.assertThat(accountPage.getErrorAccountInvalidPasswordMessageIsDisplayed())
                .as("Password validation message should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorAccountInvalidPasswordMessage())
                .as("Validation message should inform about password length requirements")
                .isEqualTo(WRONG_PASSWORD_MESSAGE);
        softly.assertAll();
    }

    @Test
    @Severity(NORMAL)
    @Tag("negative")
    @DisplayName("Check 'Sign In' with password longer than 25 characters")
    void longPasswordSignInPageTest() {
        String longPassword = "12345qwerrttfgfhdhfhdfhfdhdfhdfh";

        accountPage = new AccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();

        accountPage.inputEmailField(config.getEmail());
        accountPage.inputPasswordField(longPassword);
        accountPage.submitSignInButtonClick();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(accountPage.getLoginWarningErrorIsDisplayed())
                .as("Login warning message should be displayed for long password")
                .isTrue();

        softly.assertThat(accountPage.getLoginWarningError())
                .as("General login warning text should match expected")
                .isEqualTo(PROBLEM_REPORT);

        softly.assertThat(accountPage.getErrorAccountInvalidPasswordMessageIsDisplayed())
                .as("Password length validation message should be visible")
                .isTrue();

        softly.assertThat(accountPage.getErrorAccountInvalidPasswordMessage())
                .as("Should display password length restriction message")
                .isEqualTo(WRONG_PASSWORD_MESSAGE);
        softly.assertAll();
    }
}
