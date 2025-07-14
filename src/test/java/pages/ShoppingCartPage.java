package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShoppingCartPage extends BasePage {
    WebDriver driver;
    WebDriverWait wait;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    @FindBy(css = "span.text-bold._sale-price_6tsvav")
    private WebElement itemCost;

    @FindBy(css = "h3.cart-item-name")
    private WebElement productName;

    @FindBy(xpath = "//div[text()='Shipping']")
    private WebElement shippingCost;

    @FindBy(xpath = "//div[text()='Sub Total']")
    private WebElement subTotalCost;

    @FindBy(xpath = "//div[@data-testid='row-shipping-value']")
    private WebElement shippingCostValue;

    @FindBy(xpath = "//div[@data-testid='row-total-value']")
    private WebElement subTotalCostValue;

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

    @Step("Check title in bag")
    public String getCartTitleText() {
        return shoppingCartText.getText();
    }

    @Step("Check Quantity of items in the cart")
    public String getQuantityOfItemsText() {
        return quantityOfItems.getText();
    }

    @Step("Click on edit item")
    public void editItemButton() {
        editItem.click();
    }

    @Step("Move to 'Update Bag' button")
    public void movingToElementUpdateBagButton() {
        wait.until(ExpectedConditions.visibilityOf(updateBagButton));
        new Actions(driver)
                .moveToElement(updateBagButton)
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

    @Step("Check item cost in the cart")
    public String getItemCostInCart() {
        wait.until(ExpectedConditions.visibilityOf(itemCost));
        return itemCost.getText();
    }

    @Step("Check product name in the cart")
    public String getProductName() {
        return productName.getText();
    }

    @Step("Check shipping cost value")
    public String getShippingCostValue() {
        return shippingCostValue.getText();
    }

    @Step("Check sub total cost value")
    public String getSubTotalCostValue() {
        return subTotalCostValue.getText();
    }

    @Step("Remove product in bag Bag")
    public void removeProductInBag() {
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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
}
