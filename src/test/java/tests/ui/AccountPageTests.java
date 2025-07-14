package tests.ui;

import configs.TestPropertiesConfig;
import io.qameta.allure.Severity;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.*;
import pages.AccountPage;
import pages.HomePage;
import steps.CreateNewAccountPage;
import utils.TestDataGeneratorForCreationAccount;

import java.time.Duration;

import static constants.CommonConstants.BASE_URL;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("positive"), @Tag("defect")})
    @DisplayName("Check successful create account")
    void createAccountPageTest() {
        String email = TestDataGeneratorForCreationAccount.generateEmail();
        String firstName = TestDataGeneratorForCreationAccount.generateFirstName();
        String lastName = TestDataGeneratorForCreationAccount.generateLastName();
        String password = TestDataGeneratorForCreationAccount.generatePassword();

        accountPage = new AccountPage(driver);
        createNewAccountPage = new CreateNewAccountPage(driver);

        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickCreateAccountButton();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        createNewAccountPage.createNewAccount(email, firstName, lastName, password);

        accountPage.zipCodeField(POSTAL_CODE);
        accountPage.dropdownMonthSelectorByValue(MONTH_VALUE);
        accountPage.dropdownDaySelectorByValue(DAY_VALUE);
        accountPage.movingToElementSubmitButton();
        accountPage.clickCheckboxAcceptTerms();
        accountPage.clickSubmitButton();

        assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("'Create Account' button should be enabled").isTrue();
        assertEquals(BASE_URL + ACCOUNT_URL, accountPage.getCurrentUrl(),
                "The url values must match");
        assertEquals(ACCOUNT_CREATED_SUCCESSFUL_MESSAGE, accountPage.getSuccessfulCreatedAccountText(),
                "The user should receive a message about successful account creation");
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
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

        accountPage.zipCodeField(POSTAL_CODE);
        accountPage.dropdownMonthSelectorByValue(MONTH_VALUE);
        accountPage.dropdownDaySelectorByValue(DAY_VALUE);
        accountPage.movingToElementSubmitButton();
        accountPage.clickCheckboxAcceptTerms();

        assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("'Create Account' button is not clickable").isFalse();
        assertThat(accountPage.getErrorAccountInvalidPasswordText())
                .as("The email must be valid").isEqualTo(WRONG_EMAIL_MESSAGE);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        createNewAccountPage.createNewAccount(emptyEmail, firstName, lastName, password);

        accountPage.zipCodeField(EMPTY_POSTAL_CODE);
        accountPage.dropdownMonthSelectorByValue(MONTH_VALUE);
        accountPage.dropdownDaySelectorByValue(DAY_VALUE);
        accountPage.movingToElementSubmitButton();
        accountPage.clickCheckboxAcceptTerms();

        assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("'Create Account' button is not clickable").isFalse();
        assertThat(accountPage.getErrorAccountUnderFieldText())
                .as("The email field must not be empty").isEqualTo(EMPTY_EMAIL_MESSAGE);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
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

        accountPage.zipCodeField(POSTAL_CODE);
        accountPage.dropdownMonthSelectorByValue(MONTH_VALUE);
        accountPage.dropdownDaySelectorByValue(DAY_VALUE);
        accountPage.movingToElementSubmitButton();
        accountPage.clickCheckboxAcceptTerms();

        assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("'Create Account' button is not clickable").isFalse();
        assertThat(accountPage.getErrorEmptyFirstNameText())
                .as("The 'First name' field must not be empty").isEqualTo(EMPTY_FIRST_NAME_MESSAGE);
        assertThat(accountPage.getErrorEmptyLastNameText())
                .as("The 'Last name' field must not be empty").isEqualTo(EMPTY_LAST_NAME_MESSAGE);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
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

        accountPage.zipCodeField(POSTAL_CODE);
        accountPage.dropdownMonthSelectorByValue(MONTH_VALUE);
        accountPage.dropdownDaySelectorByValue(DAY_VALUE);
        accountPage.movingToElementSubmitButton();
        accountPage.clickCheckboxAcceptTerms();

        assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("'Create Account' button is not clickable").isFalse();
        assertThat(accountPage.getErrorEmptyPasswordText())
                .as("The password field must not be empty").isEqualTo(EMPTY_PASSWORD_MESSAGE);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
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

        accountPage.zipCodeField(EMPTY_POSTAL_CODE);
        accountPage.dropdownMonthSelectorByValue(MONTH_VALUE);
        accountPage.dropdownDaySelectorByValue(DAY_VALUE);
        accountPage.movingToElementSubmitButton();
        accountPage.clickCheckboxAcceptTerms();

        assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("'Create Account' button is not clickable").isFalse();
        assertThat(accountPage.getErrorEmptyZipCOdeText())
                .as("'Zip Code' field must not be empty").isEqualTo(EMPTY_POSTAL_CODE_MESSAGE);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
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

        accountPage.zipCodeField(POSTAL_CODE);
        accountPage.movingToElementSubmitButton();

        assertThat(accountPage.submitAccountButtonIsEnabled())
                .as("'Create Account' button is not clickable").isFalse();
    }


    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("positive"), @Tag("defect")})
    @DisplayName("Check successful 'Sign In' account")
    void signInPageTest() {
        accountPage = new AccountPage(driver);
        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();
        accountPage.inputEmailField(config.getEmail());
        accountPage.inputPasswordField(config.getPassword());
        accountPage.submitSignInButtonClick();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        assertEquals(BASE_URL + ACCOUNT_URL, accountPage.getCurrentUrl(),
                "The url values must match");
        assertEquals(ACCOUNT_SUCCESSFUL_ENTERED_MESSAGE, accountPage.getSuccessfulEnteredAccountText(),
                "The username should be used in the named accounts");
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
    @DisplayName("Check for invalid email input 'Sign In' account")
    void invalidEmailSignInPageTest() {
        String invalidEmail = "user.gmail.com";

        accountPage = new AccountPage(driver);
        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();
        accountPage.inputEmailField(invalidEmail);
        accountPage.inputPasswordField(config.getPassword());
        accountPage.submitSignInButtonClick();

        assertThat(accountPage.getErrorWarningText())
                .as("Reporting a problem logging into your account").isEqualTo(PROBLEM_REPORT);
        assertThat(accountPage.getErrorAccountUnderFieldText())
                .as("The email must be valid").isEqualTo(WRONG_EMAIL_MESSAGE);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
    @DisplayName("Check for invalid password input 'Sign In' account")
    void invalidPasswordSignInPageTest() {
        String invalidPassword = "123456789";

        accountPage = new AccountPage(driver);
        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();
        accountPage.inputEmailField(config.getEmail());
        accountPage.inputPasswordField(invalidPassword);
        accountPage.submitSignInButtonClick();

        assertThat(accountPage.getErrorWarningText())
                .as("Reporting a problem logging into your account").isEqualTo(PROBLEM_REPORT);
        assertThat(accountPage.getErrorAccountUnderFieldText())
                .as("The password must be valid").isEqualTo(WRONG_PASSWORD_MESSAGE);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
    @DisplayName("Check for empty email input 'Sign In' account")
    void emptyEmailSignInPageTest() {
        String emptyEmail = "";

        accountPage = new AccountPage(driver);
        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();
        accountPage.inputEmailField(emptyEmail);
        accountPage.inputPasswordField(config.getPassword());
        accountPage.submitSignInButtonClick();

        assertThat(accountPage.getErrorWarningText())
                .as("Reporting a problem logging into your account").isEqualTo(PROBLEM_REPORT);
        assertThat(accountPage.getErrorAccountUnderFieldText())
                .as("The field email must not be empty").isEqualTo(EMPTY_EMAIL_MESSAGE);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
    @DisplayName("Check for empty password input 'Sign In' account")
    void emptyPasswordSignInPageTest() {
        String emptyPassword = "";

        accountPage = new AccountPage(driver);
        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();
        accountPage.inputEmailField(config.getEmail());
        accountPage.inputPasswordField(emptyPassword);
        accountPage.submitSignInButtonClick();

        assertThat(accountPage.getErrorWarningText())
                .as("Reporting a problem logging into your account").isEqualTo(PROBLEM_REPORT);
        assertThat(accountPage.getErrorAccountUnderFieldText())
                .as("The field password must not be empty").isEqualTo(EMPTY_PASSWORD_MESSAGE);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
    @DisplayName("Check for empty email and empty password input 'Sign In' account")
    void emptyEmailAndEmptyPasswordSignInPageTest() {
        String emptyEmail = "";
        String emptyPassword = "";

        accountPage = new AccountPage(driver);
        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();
        accountPage.inputEmailField(emptyEmail);
        accountPage.inputPasswordField(emptyPassword);
        accountPage.submitSignInButtonClick();

        assertThat(accountPage.getErrorWarningText())
                .as("Reporting a problem logging into your account").isEqualTo(PROBLEM_REPORT);
        assertThat(accountPage.getErrorAccountUnderFieldText())
                .as("The field email must not be empty").isEqualTo(EMPTY_EMAIL_MESSAGE);
        assertThat(accountPage.getErrorEmptyPasswordText())
                .as("The field password must not be empty").isEqualTo(EMPTY_PASSWORD_MESSAGE);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
    @DisplayName("Check for password with less than 8 characters input 'Sign In' account")
    void shortPasswordSignInPageTest() {
        String shortPassword = "12345";

        accountPage = new AccountPage(driver);
        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();
        accountPage.inputEmailField(config.getEmail());
        accountPage.inputPasswordField(shortPassword);
        accountPage.submitSignInButtonClick();

        assertThat(accountPage.getErrorWarningText())
                .as("Reporting a problem logging into your account").isEqualTo(PROBLEM_REPORT);
        assertThat(accountPage.getErrorAccountUnderFieldText())
                .as("Password must be between 8 and 25 characters long").isEqualTo(WRONG_PASSWORD_MESSAGE);
    }

    @Test
    @Severity(CRITICAL)
    @Tags({@Tag("smoke"), @Tag("negative")})
    @DisplayName("Check for password with more than 25 characters input 'Sign In' account")
    void longPasswordSignInPageTest() {
        String longPassword = "12345qwerrttfgfhdhfhdfhfdhdfhdfh";

        accountPage = new AccountPage(driver);
        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();
        accountPage.inputEmailField(config.getEmail());
        accountPage.inputPasswordField(longPassword);
        accountPage.submitSignInButtonClick();

        assertThat(accountPage.getErrorWarningText())
                .as("Reporting a problem logging into your account").isEqualTo(PROBLEM_REPORT);
        assertThat(accountPage.getErrorAccountUnderFieldText())
                .as("Password must be between 8 and 25 characters long").isEqualTo(WRONG_PASSWORD_MESSAGE);
    }
}
