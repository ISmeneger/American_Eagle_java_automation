package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class ShoppingCartPage extends BasePage {
    WebDriver driver;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "h1.page-header")
    private WebElement shoppingCartText;

    @FindBy(css = "h2.text-capitalize._items-qty-msg_1yy6sp")
    private WebElement quantityOfItems;

    @FindBy(name = "editCommerceItem")
    private WebElement editItem;

    @FindBy(xpath = "//button[text()='Update Bag']")
    private WebElement updateBagButton;

    @FindBy(xpath = "//button[@aria-label='increase']")
    private WebElement addItemToBag;

    @FindBy(css = "h3.cart-item-name")
    private WebElement productName;

    @FindBy(name = "removeCommerceItem")
    private WebElement removeButton;

    @FindBy(xpath = "//h2[text()='Your bag is empty. Find something you love!']")
    private WebElement emptyBagText;

    @FindBy(name = "loginMessage")
    private WebElement signInButton;

    @FindBy(xpath = "//input[@placeholder='Email']")
    private WebElement inputEmail;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[@name='submit']")
    private WebElement submitButton;

    @FindBy(css = "h1.page-header")
    private WebElement pageCartHeader;

    @FindBy(css = "span[data-test-free-shipping]")
    private WebElement freeShipping;

    @FindBy(css = "div[data-testid='row-shipping-value']")
    private WebElement freeShippingInOrderSummary;

    @Step("Check Quantity of items in the cart")
    public String getQuantityOfItemsText() {
        return quantityOfItems.getText();
    }

    @Step("Click on edit item")
    public void editItemButton() {
        new Actions(driver)
                .scrollToElement(editItem)
                .pause(Duration.ofSeconds(2))
                .perform();
        editItem.click();
    }

    @Step("Move to 'Update Bag' button")
    public void movingToElementUpdateBagButton() {
        wait.until(ExpectedConditions.visibilityOf(updateBagButton));
        new Actions(driver)
                .moveToElement(updateBagButton)
                .pause(Duration.ofSeconds(2))
                .perform();
    }

    @Step("Add item to bag")
    public void addItemToBag() {
        addItemToBag.click();
    }

    @Step("Update Bag")
    public void updateBag() {
        updateBagButton.click();
    }

    @Step("Check product name in the cart")
    public String getProductName() {
        return productName.getText();
    }

    @Step("Remove product in bag")
    public void removeProductInBag() {
        new Actions(driver)
                .scrollToElement(removeButton)
                .pause(Duration.ofSeconds(2))
                .perform();
        removeButton.click();
    }

    @Step("Check bag is empty")
    public String getItemEmptyText() {
        wait.until(ExpectedConditions.visibilityOf(emptyBagText));
        return emptyBagText.getText();
    }

    @Step("Click on button 'Sign in'")
    public void signInButtonClick() {
        signInButton.click();
    }

    @Step("Input email field")
    public void inputEmailField(String email) {
        wait.until(ExpectedConditions.visibilityOf(inputEmail)).sendKeys(email);
    }

    @Step("Input password field")
    public void inputPasswordField(String password) {
        inputPassword.sendKeys(password);
    }

    @Step("Click on submit button")
    public void submitButtonClick() {
        submitButton.click();
    }

    @Step("Check page header in cart")
    public String getPageCartHeaderText() {
        wait.until(ExpectedConditions.visibilityOf(pageCartHeader));
        return pageCartHeader.getText();
    }

    @Step("Check that free shipping displayed in cart")
    public boolean isFreeShippingMessageDisplayed() {
        return freeShipping.isDisplayed();
    }

    @Step("Get the text about free shipping available in 'Order Summary")
    public String getFreeShippingTextInOrderSummary() {
        return freeShippingInOrderSummary.getText();
    }
}
