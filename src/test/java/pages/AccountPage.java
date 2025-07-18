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

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountPage extends BasePage {
    WebDriver driver;

    public static final String VALUE_NAME = "value";

    public AccountPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "svg[data-testid='icon-account']")
    private WebElement iconAccount;

    @FindBy(xpath = "//a[@data-test='register-button']")
    private WebElement createAccountButton;

    @FindBy(xpath = "//input[@placeholder='Email']")
    private WebElement inputEmail;

    @FindBy(xpath = "//input[@name='firstname']")
    private WebElement inputFirstName;

    @FindBy(xpath = "//input[@name='lastname']")
    private WebElement inputLastName;

    @FindBy(xpath = "//h2[text()='Sign Up for Emails & Texts']")
    private WebElement signUpText;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement inputPassword;

    @FindBy(xpath = "//input[@name='confirm_password']")
    private WebElement confirmPassword;

    @FindBy(xpath = "//input[@name='postalCode']")
    private WebElement zipCode;

    @FindBy(xpath = "//select[@name='month']")
    private WebElement dropdownSelectMenuMonth;

    @FindBy(xpath = "//select[@name='day']")
    private WebElement dropdownSelectMenuDay;

    @FindBy(name = "acceptTerms")
    private WebElement checkboxAcceptTerms;

    @FindBy(xpath = "//button[@name='submit']")
    private WebElement submitAccountButton;

    @FindBy(xpath = "//h6[text()='Account created!']")
    private WebElement accountCreatedText;

    @FindBy(xpath = "//button[@name='submit']")
    private WebElement submitSignInButton;

    @FindBy(css = "h2.modal-title")
    private WebElement successfulAccountText;

    @FindBy(css = "h6.alert-header")
    private WebElement errorWarningText;

    @FindBy(css = "div[data-label-code='error.account.email.empty']")
    private WebElement errorAccountEmptyEmailFieldText;

    @FindBy(css = "div[data-label-code='error.account.password.empty']")
    private WebElement errorAccountEmptyPasswordFieldText;

    @FindBy(css = "div[data-label-code='error.account.email.invalid']")
    private WebElement errorAccountInvalidEmailText;

    @FindBy(css = "div[data-label-code='error.account.login.passwordInvalid']")
    private WebElement errorAccountInvalidPasswordText;

    @FindBy(css = "div[data-label-code='error.checkout.shippingAddress.firstName.empty']")
    private WebElement errorEmptyFirstNameText;

    @FindBy(css = "div[data-label-code='error.checkout.shippingAddress.lastName.empty']")
    private WebElement errorEmptyLastNameText;

    @FindBy(css = "div[data-label-code='error.account.loyalty.postalCode.empty']")
    private WebElement errorEmptyZipCodeText;

    @Step("Input email field")
    public void inputEmailField(String email) {
        wait.until(ExpectedConditions.visibilityOf(inputEmail)).sendKeys(email);
    }

    @Step("Input first name field")
    public void inputFirstNameField(String firstName) {
        inputFirstName.sendKeys(firstName);
    }

    @Step("Input last name field")
    public void inputLastNameField(String lastName) {
        inputLastName.sendKeys(lastName);
    }

    @Step("Input password field")
    public void inputPasswordField(String password) {
        inputPassword.sendKeys(password);
    }

    @Step("Confirm password field")
    public void confirmPasswordField(String password) {
        confirmPassword.sendKeys(password);
    }

    @Step("Zip code field")
    public void enterZipCode(String postalCode) {
        zipCode.sendKeys(postalCode);
    }

    @Step("Click dropdown month and choose value")
    public void dropdownMonthSelectorByValue(String value) {
        Select select = new Select(dropdownSelectMenuMonth);
        select.selectByValue(value);
        assertEquals(value, select.getFirstSelectedOption().getDomProperty(VALUE_NAME));
    }

    @Step("Click dropdown day and choose value")
    public void dropdownDaySelectorByValue(String value) {
        Select select = new Select(dropdownSelectMenuDay);
        select.selectByValue(value);
        assertEquals(value, select.getFirstSelectedOption().getDomProperty(VALUE_NAME));
    }

    @Step("Select BirthDate")
    public void selectBirthDate(String valueMonth, String valueDay) {
        dropdownMonthSelectorByValue(valueMonth);
        dropdownDaySelectorByValue(valueDay);
    }

    @Step("Move to 'Create Account' button")
    public void scrollToSubmitButton() {
        new Actions(driver)
                .moveToElement(submitAccountButton)
                .pause(Duration.ofSeconds(2))
                .perform();
    }

    @Step("Click checkbox 'I Accept'")
    public void acceptTermsAndConditions() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", checkboxAcceptTerms);
    }

    public boolean isTermsCheckboxSelected() {
        return checkboxAcceptTerms.isSelected();
    }

    @Step("Check 'Create Account' button is enabled")
    public Boolean submitAccountButtonIsEnabled() {
        return submitAccountButton.isEnabled();
    }

    @Step("Click submit button")
    public void clickSubmitButton() {
        submitAccountButton.click();
    }

    @Step("Check successful created account")
    public String getSuccessfulCreatedAccountText() {
        return accountCreatedText.getText();
    }

    @Step("Click on 'Sign In' button")
    public void submitSignInButtonClick() {
        submitSignInButton.click();
    }

    @Step("Check successful entered to account")
    public String getSuccessfulEnteredAccountText() {
        return successfulAccountText.getText();
    }

    @Step("Get text about problem")
    public String getLoginWarningError() {
        return errorWarningText.getText();
    }

    @Step("Checking that the form error is displayed specifically for email field")
    public boolean getLoginWarningErrorIsDisplayed() {
        return errorWarningText.isDisplayed();
    }

    @Step("Get text describing the problem under the password, when it's not valid")
    public String getErrorEmailMessage() {
        return errorAccountInvalidEmailText.getText();
    }

    @Step("Checking that the form error is displayed specifically for email field")
    public boolean getErrorEmailMessageIsDisplayed() {
        return errorAccountInvalidEmailText.isDisplayed();
    }

    @Step("Get text describing the problem under the password, when it's not valid")
    public String getErrorAccountInvalidPasswordMessage() {
        return errorAccountInvalidPasswordText.getText();
    }

    @Step("Checking that the form error is displayed specifically for password field")
    public boolean getErrorAccountInvalidPasswordMessageIsDisplayed() {
        return errorAccountInvalidPasswordText.isDisplayed();
    }

    @Step("Get text describing the problem under the email field, when it's empty")
    public String getErrorEmptyEmailMessage() {
        return errorAccountEmptyEmailFieldText.getText();
    }

    @Step("Checking that the empty form error is displayed specifically for email field")
    public boolean getErrorEmptyEmailMessageIsDisplayed() {
        return errorAccountEmptyEmailFieldText.isDisplayed();
    }

    @Step("Get text describing the problem under the password field, when it's empty")
    public String getErrorEmptyPasswordMessage() {
        return errorAccountEmptyPasswordFieldText.getText();
    }

    @Step("Checking that the empty form error is displayed specifically for password field")
    public boolean getErrorEmptyPasswordMessageIsDisplayed() {
        return errorAccountEmptyPasswordFieldText.isDisplayed();
    }

    @Step("Get text describing the problem under the 'First Name' field, when it's empty")
    public String getErrorEmptyFirstNameMessage() {
        return errorEmptyFirstNameText.getText();
    }

    @Step("Checking that the empty form error is displayed specifically for 'First Name' field")
    public boolean getErrorEmptyFirstNameMessageIsDisplayed() {
        return errorEmptyFirstNameText.isDisplayed();
    }

    @Step("Get text describing the problem under the 'Last Name' field, when it's empty")
    public String getErrorEmptyLastNameMessage() {
        return errorEmptyLastNameText.getText();
    }

    @Step("Checking that the empty form error is displayed specifically for 'Last Name' field")
    public boolean getErrorEmptyLastNameMessageIsDisplayed() {
        return errorEmptyLastNameText.isDisplayed();
    }

    @Step("Get text describing the problem under the 'Zip Cde' field, when it's empty")
    public String getErrorEmptyZipCOdeMessage() {
        return errorEmptyZipCodeText.getText();
    }

    @Step("Checking that the empty form error is displayed specifically for 'Zip Cde' field")
    public boolean getErrorEmptyZipCOdeMessageIsDisplayed() {
        return errorEmptyZipCodeText.isDisplayed();
    }
}
