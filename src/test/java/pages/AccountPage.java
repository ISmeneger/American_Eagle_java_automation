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
import org.openqa.selenium.support.ui.WebDriverWait;

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

    @FindBy(xpath = "//div[@data-testid='form-error']")
    private WebElement errorAccountUnderFieldText;

    @FindBy(xpath = "//div[@data-testid='form-error' and text()='Please enter your password.']")
    private WebElement errorAccountEmptyPasswordFieldText;

    @FindBy(xpath = "//div[@data-testid='form-error' and text()='Please enter a valid email address.']")
    private WebElement errorAccountInvalidPasswordText;

    @FindBy(xpath = "//div[@data-testid='form-error' and text()='Please enter your first name.']")
    private WebElement errorEmptyFirstNameText;

    @FindBy(xpath = "//div[@data-testid='form-error' and text()='Please enter your last name.']")
    private WebElement errorEmptyLastNameText;

    @FindBy(xpath = "//div[@data-testid='form-error' and text()='Please enter your zip/postal code.']")
    private WebElement errorEmptyZipCodeText;

    @Step("Click account button")
    public void clickAccountButton() {
        iconAccount.click();
    }

    @Step("Click Create account button")
    public void clickCreateAccountButton() {
        createAccountButton.click();
    }

    @Step("Input email field")
    public void inputEmailField(String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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
    public void zipCodeField(String postalCode) {
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

    @Step("Move to 'Create Account' button")
    public void movingToElementSubmitButton() {
        new Actions(driver)
                .moveToElement(submitAccountButton)
                .perform();
    }

    @Step("Click checkbox 'I Accept'")
    public void clickCheckboxAcceptTerms() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", checkboxAcceptTerms);
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
    public String getErrorWarningText() {
        return errorWarningText.getText();
    }

    @Step("Get text describing the problem under the email and password field")
    public String getErrorAccountUnderFieldText() {
        return errorAccountUnderFieldText.getText();
    }

    @Step("Get text describing the problem under the email, when it's not valid")
    public String getErrorAccountInvalidPasswordText() {
        return errorAccountInvalidPasswordText.getText();
    }

    @Step("Get text describing the problem under the password field, when it's empty")
    public String getErrorEmptyPasswordText() {
        return errorAccountEmptyPasswordFieldText.getText();
    }

    @Step("Get text describing the problem under the 'First Name' field, when it's empty")
    public String getErrorEmptyFirstNameText() {
        return errorEmptyFirstNameText.getText();
    }

    @Step("Get text describing the problem under the 'Last Name' field, when it's empty")
    public String getErrorEmptyLastNameText() {
        return errorEmptyLastNameText.getText();
    }

    @Step("Get text describing the problem under the 'Zip Cde' field, when it's empty")
    public String getErrorEmptyZipCOdeText() {
        return errorEmptyZipCodeText.getText();
    }
}
