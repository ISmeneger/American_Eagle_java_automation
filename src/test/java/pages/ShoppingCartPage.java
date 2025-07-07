package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ShoppingCartPage extends BasePage {
    WebDriver driver;
    WebDriverWait wait;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//h1[text()='Shopping Bag']")
    WebElement shoppingCartText;

    @FindBy(css = "h1.page-header")
    private WebElement authorizedCartText;

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

    @FindBy(xpath = "//a[text()='AE Boxy Destination Graphic T-Shirt']")
    private WebElement productName;

    @FindBy(xpath = "//div[text()='Shipping']")
    private WebElement shippingCost;

    @FindBy(xpath = "//div[text()='Sub Total']")
    private WebElement subTotalCost;

    @FindBy(xpath = "//div[@data-testid='row-shipping-value']")
    private WebElement shippingCostValue;

    @FindBy(xpath = "//div[@data-testid='row-total-value']")
    private WebElement subTotalCostValue;

    @Step("Check title in bag")
    public String getCartTitleText() {
        return shoppingCartText.getText();
    }

    @Step("Check title in bag authorized user")
    public String getCartTitleAuthorizedUserText() {
        return authorizedCartText.getText();
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
}
