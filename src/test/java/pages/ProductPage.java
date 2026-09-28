package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage extends BasePage {

    private final WebDriver driver;

    @FindBy(css = "div[data-test-dropdown-toggle]")
    private WebElement dropdownSizeToggle;

    @FindBy(css = "ul.dropdown-menu li:not(.visually-disabled) a[role='menuitem']")
    private List<WebElement> availableSizes;

    @FindBy(css = "div[data-test-dropdown-toggle] span[data-test-text]")
    private WebElement selectedSizeText;

    @FindBy(css = "div.product-sale-price")
    private WebElement productPrice;

    @FindBy(name = "addToBag")
    private WebElement addToBagButton;

    @FindBy(xpath = "//h2[text()='Added to bag!']")
    private WebElement successfulAddedToBag;

    @FindBy(css = "button[data-test-btn='viewBag']")
    private WebElement viewItemInBagButton;

    @FindBy(xpath = "//button[@aria-label='increase']")
    private WebElement increaseQuantityButton;

    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Select first available size")
    public void selectFirstAvailableSize() {

        WebElement sizeDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        dropdownSizeToggle
                )
        );

        new Actions(driver)
                .scrollToElement(sizeDropdown)
                .perform();

        sizeDropdown.click();

        wait.until(
                ExpectedConditions.attributeToBe(
                        dropdownSizeToggle,
                        "aria-expanded",
                        "true"
                )
        );

        wait.until(
                ExpectedConditions.visibilityOfAllElements(
                        availableSizes
                )
        );

        if (availableSizes.isEmpty()) {
            throw new RuntimeException(
                    "No available sizes found"
            );
        }

        WebElement firstAvailableSize =
                availableSizes.get(0);

        String expectedSize = firstAvailableSize
                .findElement(
                        By.cssSelector("span.sku-size")
                )
                .getText()
                .trim();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        firstAvailableSize
                )
        ).click();

        wait.until(
                ExpectedConditions.attributeToBe(
                        dropdownSizeToggle,
                        "aria-expanded",
                        "false"
                )
        );

        wait.until(driver ->
                selectedSizeText
                        .getText()
                        .trim()
                        .equals(expectedSize)
        );
    }

    @Step("Get selected product size")
    public String getSelectedSize() {
        return wait.until(
                ExpectedConditions.visibilityOf(selectedSizeText)
        ).getText().trim();
    }

    @Step("Get product price")
    public String getProductPrice() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        productPrice
                )
        ).getText();
    }

    @Step("Add product to bag")
    public void clickAddToBagButton() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        addToBagButton
                )
        ).click();

        wait.until(
                ExpectedConditions.visibilityOf(
                        successfulAddedToBag
                )
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        viewItemInBagButton
                )
        );
    }

    @Step("Get successful added to bag message")
    public String getSuccessfulAddedToBagText() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        successfulAddedToBag
                )
        ).getText();
    }

    @Step("Open shopping bag")
    public void openShoppingBag() {

        WebElement viewBagButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        viewItemInBagButton
                )
        );

        viewBagButton.click();

        WebDriverWait navigationWait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        navigationWait.until(
                ExpectedConditions.urlContains("/cart")
        );
    }

    @Step("Check increase quantity button is enabled")
    public boolean isIncreaseQuantityButtonEnabled() {
        return increaseQuantityButton.isEnabled();
    }

    @Step("Increase product quantity")
    public void increaseQuantity() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        increaseQuantityButton
                )
        ).click();
    }

    @Step("Increase product quantity until button is disabled")
    public int increaseQuantityUntilDisabled() {

        int quantity = 1;

        while (isIncreaseQuantityButtonEnabled()) {
            increaseQuantity();
            quantity++;
        }

        return quantity;
    }
}