package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ShoppingCartPage extends BasePage {

    private final WebDriver driver;

    @FindBy(css = "h2[data-test-items-qty-msg]")
    private WebElement quantityOfItems;

    @FindBy(css = "button[data-test-btn='editCommerceItem']")
    private WebElement editItem;

    @FindBy(xpath = "//button[text()='Update Bag']")
    private WebElement updateBagButton;

    @FindBy(xpath = "//button[@aria-label='increase']")
    private WebElement increaseQuantityButton;

    @FindBy(css = "h3.cart-item-name")
    private WebElement productName;

    @FindBy(css = "[data-testid='size']")
    private WebElement productSize;

    @FindBy(css = "button[data-test-btn='removeCommerceItem']")
    private WebElement removeButton;

    @FindBy(xpath = "//h2[text()='Your bag is empty. Find something you love!']")
    private WebElement emptyBagText;

    @FindBy(css = "a[data-testid='sign-in-link']")
    private WebElement signInButton;

    @FindBy(css = "h1.page-header")
    private WebElement pageCartHeader;

    @FindBy(css = "span[data-test-free-shipping]")
    private WebElement freeShipping;

    @FindBy(css = "span[data-test-cart-item-sale-price]")
    private WebElement cartProductPrice;

    @FindBy(css = "[data-testid='row-total-value']")
    private WebElement subtotalValue;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Get quantity of items in the cart")
    public String getQuantityOfItemsText() {
        return wait.until(
                ExpectedConditions.visibilityOf(quantityOfItems)
        ).getText();
    }

    @Step("Click edit item button")
    public void editItemButton() {

        WebElement editButton = wait.until(
                ExpectedConditions.elementToBeClickable(editItem)
        );

        new Actions(driver)
                .scrollToElement(editButton)
                .perform();

        wait.until(
                ExpectedConditions.elementToBeClickable(editButton)
        ).click();
    }

    @Step("Move to 'Update Bag' button")
    public void movingToElementUpdateBagButton() {

        WebElement updateButton = wait.until(
                ExpectedConditions.visibilityOf(updateBagButton)
        );

        new Actions(driver)
                .scrollToElement(updateButton)
                .moveToElement(updateButton)
                .perform();
    }

    @Step("Increase product quantity")
    public void increaseProductQuantity() {
        wait.until(
                ExpectedConditions.elementToBeClickable(increaseQuantityButton)
        ).click();
    }

    @Step("Update bag")
    public void updateBag() {
        wait.until(
                ExpectedConditions.elementToBeClickable(updateBagButton)
        ).click();
    }

    @Step("Get product name in the cart")
    public String getProductName() {
        return wait.until(
                ExpectedConditions.visibilityOf(productName)
        ).getText();
    }

    @Step("Get product size in cart")
    public String getProductSize() {
        String sizeText = wait.until(
                ExpectedConditions.visibilityOf(productSize)
        ).getText();

        return sizeText
                .replace("Size:", "")
                .trim();
    }

    @Step("Remove product from bag")
    public void removeProductInBag() {

        WebElement remove = wait.until(
                ExpectedConditions.visibilityOf(removeButton)
        );

        new Actions(driver)
                .scrollToElement(remove)
                .perform();

        wait.until(
                ExpectedConditions.elementToBeClickable(remove)
        ).click();
    }

    @Step("Get empty bag message")
    public String getItemEmptyText() {
        return wait.until(
                ExpectedConditions.visibilityOf(emptyBagText)
        ).getText();
    }

    @Step("Click 'Sign In' button in cart")
    public void signInButtonClick() {
        wait.until(
                ExpectedConditions.elementToBeClickable(signInButton)
        ).click();
    }

    @Step("Get cart page header")
    public String getPageCartHeaderText() {
        return wait.until(
                ExpectedConditions.visibilityOf(pageCartHeader)
        ).getText();
    }

    @Step("Check that free shipping is displayed in cart")
    public boolean isFreeShippingMessageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(freeShipping)
        ).isDisplayed();
    }

    @Step("Get product price in cart")
    public String getCartPrice() {
        return wait.until(
                ExpectedConditions.visibilityOf(cartProductPrice)
        ).getText();
    }

    @Step("Get subtotal value in cart")
    public String getSubtotalText() {
        return wait.until(
                ExpectedConditions.visibilityOf(subtotalValue)
        ).getText().trim();
    }
}