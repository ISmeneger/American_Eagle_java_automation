package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class AccountPage extends BasePage {

    WebDriver driver;

    public AccountPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // =========================
    // ACCOUNT / SIGN IN
    // =========================

    @FindBy(css = "svg[data-testid='icon-account']")
    private WebElement iconAccount;

    @FindBy(xpath = "//a[@data-test='register-button']")
    private WebElement createAccountButton;

    @FindBy(xpath = "//input[@placeholder='Email']")
    private WebElement inputEmail;

    @FindBy(id = "kc-login")
    private WebElement continueButton;

    @FindBy(id = "PASSWORD")
    private WebElement passwordSignInMethod;

    // =========================
    // CREATE ACCOUNT
    // =========================

    @FindBy(id = "firstName")
    private WebElement inputFirstName;

    @FindBy(id = "lastName")
    private WebElement inputLastName;

    @FindBy(id = "password")
    private WebElement inputPassword;

    @FindBy(id = "password-confirm")
    private WebElement confirmPassword;

    @FindBy(id = "postalCode")
    private WebElement zipCode;

    @FindBy(id = "birthMonth")
    private WebElement dropdownSelectMenuMonth;

    @FindBy(id = "birthDay")
    private WebElement dropdownSelectMenuDay;

    @FindBy(id = "termsAccepted")
    private WebElement checkboxAcceptTerms;

    @FindBy(id = "kc-register-button")
    private WebElement submitAccountButton;

    // =========================
    // RESULT / MESSAGES
    // =========================

    @FindBy(xpath = "//h6[text()='Account created!']")
    private WebElement accountCreatedText;

    @FindBy(css = "h2.modal-title")
    private WebElement successfulAccountText;

    @FindBy(css = "h6.alert-header")
    private WebElement errorWarningText;

    @FindBy(xpath = "//input[@id='email']/following-sibling::span[contains(@class,'kc-feedback-text')]")
    private WebElement emailValidationErrorText;

    @FindBy(xpath = "//input[@id='password']/following-sibling::span[contains(@class,'kc-feedback-text')]")
    private WebElement errorAccountEmptyPasswordFieldText;

    @FindBy(css = "div[data-label-code='error.account.login.passwordInvalid']")
    private WebElement errorAccountInvalidPasswordText;

    @FindBy(xpath = "//input[@id='firstName']/following-sibling::span[contains(@class,'kc-feedback-text')]")
    private WebElement errorEmptyFirstNameText;

    @FindBy(xpath = "//input[@id='lastName']/following-sibling::span[contains(@class,'kc-feedback-text')]")
    private WebElement errorEmptyLastNameText;

    @FindBy(xpath = "//input[@id='postalCode']/following-sibling::span[contains(@class,'kc-feedback-text')]")
    private WebElement errorEmptyZipCodeText;

    @FindBy(xpath = "//span[contains(@class,'kc-feedback-text') and normalize-space()='Please enter a valid email address.']")
    private WebElement signInInvalidEmailErrorText;

    // =========================
    // COMMON INPUT METHOD
    // =========================

    private void typeIntoField(WebElement field, String value) {

        WebElement input = wait.until(
                ExpectedConditions.elementToBeClickable(field)
        );

        input.clear();
        input.sendKeys(value);
    }

    // =========================
    // INPUT METHODS
    // =========================

    @Step("Input email field")
    public void inputEmailField(String email) {
        typeIntoField(inputEmail, email);
    }

    @Step("Input first name field")
    public void inputFirstNameField(String firstName) {
        typeIntoField(inputFirstName, firstName);
    }

    @Step("Input last name field")
    public void inputLastNameField(String lastName) {
        typeIntoField(inputLastName, lastName);
    }

    @Step("Input password field")
    public void inputPasswordField(String password) {
        typeIntoField(inputPassword, password);
    }

    @Step("Confirm password field")
    public void confirmPasswordField(String password) {
        typeIntoField(confirmPassword, password);
    }

    @Step("Input zip code field")
    public void enterZipCode(String postalCode) {
        typeIntoField(zipCode, postalCode);
    }

    // =========================
    // SIGN IN
    // =========================

    @Step("Click Continue button")
    public void continueButtonClick() {
        wait.until(
                ExpectedConditions.elementToBeClickable(continueButton)
        ).click();
    }

    @Step("Select password as sign-in method")
    public void selectPasswordSignInMethod() {
        wait.until(
                ExpectedConditions.elementToBeClickable(passwordSignInMethod)
        ).click();
    }

    @Step("Click on 'Sign In' / Continue button")
    public void submitSignInButtonClick() {
        wait.until(
                ExpectedConditions.elementToBeClickable(continueButton)
        ).click();
    }

    // =========================
    // BIRTH DATE
    // =========================

    @Step("Select birth month: {value}")
    public void dropdownMonthSelectorByValue(String value) {

        WebElement monthDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        dropdownSelectMenuMonth
                )
        );

        new Select(monthDropdown)
                .selectByValue(value);
    }

    @Step("Select birth day: {value}")
    public void dropdownDaySelectorByValue(String value) {

        WebElement dayDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        dropdownSelectMenuDay
                )
        );

        new Select(dayDropdown)
                .selectByValue(value);
    }

    @Step("Select birth date")
    public void selectBirthDate(
            String valueMonth,
            String valueDay
    ) {
        dropdownMonthSelectorByValue(valueMonth);
        dropdownDaySelectorByValue(valueDay);
    }

    @Step("Get selected birth month")
    public String getSelectedBirthMonth() {

        WebElement monthDropdown = wait.until(
                ExpectedConditions.visibilityOf(
                        dropdownSelectMenuMonth
                )
        );

        return new Select(monthDropdown)
                .getFirstSelectedOption()
                .getDomProperty("value");
    }

    @Step("Get selected birth day")
    public String getSelectedBirthDay() {

        WebElement dayDropdown = wait.until(
                ExpectedConditions.visibilityOf(
                        dropdownSelectMenuDay
                )
        );

        return new Select(dayDropdown)
                .getFirstSelectedOption()
                .getDomProperty("value");
    }

    // =========================
    // CREATE ACCOUNT BUTTON
    // =========================

    @Step("Move to 'Create Account' button")
    public void scrollToSubmitButton() {

        WebElement button = wait.until(
                ExpectedConditions.visibilityOf(submitAccountButton)
        );

        new Actions(driver)
                .scrollToElement(button)
                .moveToElement(button)
                .perform();
    }

    @Step("Click checkbox 'I Accept'")
    public void acceptTermsAndConditions() {

        WebElement checkbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        org.openqa.selenium.By.id("termsAccepted")
                )
        );

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                checkbox
        );
    }

    @Step("Check terms checkbox is selected")
    public boolean isTermsCheckboxSelected() {
        return checkboxAcceptTerms.isSelected();
    }

    @Step("Check 'Create Account' button is enabled")
    public Boolean submitAccountButtonIsEnabled() {
        return wait.until(
                ExpectedConditions.visibilityOf(submitAccountButton)
        ).isEnabled();
    }

    @Step("Click 'Create Account' button")
    public void clickSubmitButton() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        submitAccountButton
                )
        ).click();
    }

    // =========================
    // SUCCESS MESSAGES
    // =========================

    @Step("Check successful created account")
    public String getSuccessfulCreatedAccountText() {

        return wait.until(
                ExpectedConditions.visibilityOf(
                        accountCreatedText
                )
        ).getText();
    }

    @Step("Check successful entered to account")
    public String getSuccessfulEnteredAccountText() {

        return wait.until(
                ExpectedConditions.visibilityOf(
                        successfulAccountText
                )
        ).getText();
    }

    // =========================
    // LOGIN ERRORS
    // =========================

    @Step("Get text about login problem")
    public String getLoginWarningError() {
        return wait.until(
                ExpectedConditions.visibilityOf(errorWarningText)
        ).getText();
    }

    @Step("Check login warning is displayed")
    public boolean getLoginWarningErrorIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(errorWarningText)
        ).isDisplayed();
    }

    @Step("Get invalid password error message")
    public String getErrorAccountInvalidPasswordMessage() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        errorAccountInvalidPasswordText
                )
        ).getText();
    }

    @Step("Check invalid password error is displayed")
    public boolean getErrorAccountInvalidPasswordMessageIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        errorAccountInvalidPasswordText
                )
        ).isDisplayed();
    }

    // =========================
    // REGISTRATION VALIDATION ERRORS
    // =========================

    @Step("Get email validation error message")
    public String getEmailValidationErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        emailValidationErrorText
                )
        ).getText();
    }

    @Step("Check email validation error is displayed")
    public boolean isEmailValidationErrorDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        emailValidationErrorText
                )
        ).isDisplayed();
    }

    @Step("Get empty password error message")
    public String getErrorEmptyPasswordMessage() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        errorAccountEmptyPasswordFieldText
                )
        ).getText();
    }

    @Step("Check empty password error is displayed")
    public boolean getErrorEmptyPasswordMessageIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        errorAccountEmptyPasswordFieldText
                )
        ).isDisplayed();
    }

    @Step("Get empty First Name error message")
    public String getErrorEmptyFirstNameMessage() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        errorEmptyFirstNameText
                )
        ).getText();
    }

    @Step("Check empty First Name error is displayed")
    public boolean getErrorEmptyFirstNameMessageIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        errorEmptyFirstNameText
                )
        ).isDisplayed();
    }

    @Step("Get empty Last Name error message")
    public String getErrorEmptyLastNameMessage() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        errorEmptyLastNameText
                )
        ).getText();
    }

    @Step("Check empty Last Name error is displayed")
    public boolean getErrorEmptyLastNameMessageIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        errorEmptyLastNameText
                )
        ).isDisplayed();
    }

    @Step("Get empty Zip Code error message")
    public String getErrorEmptyZipCodeMessage() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        errorEmptyZipCodeText
                )
        ).getText();
    }

    @Step("Check empty Zip Code error is displayed")
    public boolean getErrorEmptyZipCodeMessageIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        errorEmptyZipCodeText
                )
        ).isDisplayed();
    }

    @Step("Get invalid email error message on Sign In page")
    public String getSignInInvalidEmailErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        signInInvalidEmailErrorText
                )
        ).getText();
    }

    @Step("Check invalid email error is displayed on Sign In page")
    public boolean isSignInInvalidEmailErrorDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        signInInvalidEmailErrorText
                )
        ).isDisplayed();
    }
}