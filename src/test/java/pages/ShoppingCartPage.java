package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ShoppingCartPage extends BasePage {

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
    private List<WebElement> cartSalePrices;

    @FindBy(css = "span[data-test-cart-item-price]")
    private List<WebElement> cartRegularPrices;

    @FindBy(css = "[data-test-total-row] " +
            "[data-testid='row-total-value']")
    private WebElement subtotalValue;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get quantity of items in cart")
    public String getQuantityOfItemsText() {
        return wait.until(
                ExpectedConditions.visibilityOf(quantityOfItems)
        ).getText();
    }

    @Step("Click 'Edit Item' button")
    public void clickEditItemButton() {
        clickWithOverlayRetry(editItem);
    }

    @Step("Move to 'Update Bag' button")
    public void moveToUpdateBagButton() {

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
        clickWithOverlayRetry(increaseQuantityButton);
    }

    @Step("Update bag")
    public void updateBag() {
        clickWithOverlayRetry(updateBagButton);
    }

    @Step("Get product name in cart")
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
    public void removeProductFromBag() {

        WebElement remove = wait.until(
                ExpectedConditions.visibilityOf(removeButton)
        );

        new Actions(driver)
                .scrollToElement(remove)
                .perform();

        clickWithOverlayRetry(remove);
    }

    @Step("Get empty bag message")
    public String getEmptyBagMessage() {
        return wait.until(
                ExpectedConditions.visibilityOf(emptyBagText)
        ).getText();
    }

    @Step("Click 'Sign In' button in cart")
    public void clickSignInButton() {
        clickWithOverlayRetry(signInButton);
    }

    @Step("Get cart page header")
    public String getCartPageHeader() {
        return wait.until(
                ExpectedConditions.visibilityOf(pageCartHeader)
        ).getText();
    }

    @Step("Check free shipping message is displayed")
    public boolean isFreeShippingMessageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(freeShipping)
        ).isDisplayed();
    }

    @Step("Get product price in cart")
    public String getProductPriceInCart() {

        if (!cartSalePrices.isEmpty()
                && cartSalePrices.get(0).isDisplayed()) {
            return cartSalePrices.get(0).getText().trim();
        }

        if (!cartRegularPrices.isEmpty()
                && cartRegularPrices.get(0).isDisplayed()) {
            return cartRegularPrices.get(0).getText().trim();
        }

        throw new IllegalStateException(
                "Product price in cart is not displayed"
        );
    }

    @Step("Get subtotal value in cart")
    public String getSubtotalText() {
        return wait.until(
                ExpectedConditions.visibilityOf(subtotalValue)
        ).getText().trim();
    }
}