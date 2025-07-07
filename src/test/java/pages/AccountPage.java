package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountPage extends BasePage {
    WebDriver driver;
    WebDriverWait wait;

    public static final String VALUE_NAME = "value";

    public AccountPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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

    @FindBy(xpath = "//input[@id='toggle-ember15']")
    private WebElement checkboxAEEmails;

    @FindBy(xpath = "//input[@id='toggle-ember16']")
    private WebElement checkboxAerieEmails;

    @FindBy(xpath = "//*[@id='main-content-focus']/div[2]/div/div/div/form/div[3]/label")
    private WebElement checkboxAcceptTerms;

    @FindBy(xpath = "//button[@name='submit']")
    private WebElement submitAccountButton;

    @FindBy(xpath = "//h6[text()='Account created!']")
    private WebElement accountCreatedText;

    @FindBy(xpath = "//button[@name='submit']")
    private WebElement submitSignInButton;

    @FindBy(css = "h2.modal-title")
    private WebElement successfulAccountText;

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
        inputEmail.sendKeys(email);
    }

    @Step("Input first name field")
    public void inputFirstNameField(String firstName) {
        inputFirstName.sendKeys(firstName);
    }

    @Step("Input last name field")
    public void inputLastNameField(String lastName) {
        inputLastName.sendKeys(lastName);
    }

    @Step("Move to text 'Sign Up for Emails & Texts'")
    public void movingToElementSignUpText() {
        new Actions(driver)
                .moveToElement(signUpText)
                .perform();
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

    @Step("Check dropdown month and choose value")
    public void dropdownMonthSelectorByValue(String value) {
        Select select = new Select(dropdownSelectMenuMonth);
        select.selectByValue(value);
        assertEquals(value, select.getFirstSelectedOption().getDomProperty(VALUE_NAME));
    }

    @Step("Check dropdown day and choose value")
    public void dropdownDaySelectorByValue(String value) {
        Select select = new Select(dropdownSelectMenuDay);
        select.selectByValue(value);
        assertEquals(value, select.getFirstSelectedOption().getDomProperty(VALUE_NAME));
    }

    @Step("Move to text 'Sign Up for Emails & Texts'")
    public void movingToElementSubmitButton() {
        new Actions(driver)
                .moveToElement(submitAccountButton)
                .perform();
    }

    @Step("Check checkbox AE Emails")
    public void clickCheckboxAEEmails() {
        checkboxAEEmails.click();
    }

    @Step("Check checkbox Aerie Emails")
    public void clickCheckboxAerieEmails() {
        checkboxAerieEmails.click();
    }

    @Step("Check checkbox Aerie Emails")
    public void clickCheckboxAcceptTerms() {
        checkboxAcceptTerms.click();
    }

    @Step("Click submit button")
    public void clickSubmitButton() {
        submitAccountButton.click();
    }

    @Step("Check successful created account")
    public String getSuccessfulCreatedAccountText() {
        return accountCreatedText.getText();
    }

    @Step("Click on submit button")
    public void submitSignInButtonClick() {
        submitSignInButton.click();
    }

    @Step("Check successful entered to account")
    public String getSuccessfulEnteredAccountText() {
        return successfulAccountText.getText();
    }
}
