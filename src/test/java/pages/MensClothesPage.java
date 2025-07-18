package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class MensClothesPage extends BasePage {
    WebDriver driver;

    public MensClothesPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[text()='Men']")
    private WebElement menFormMenu;

    @FindBy(xpath = "//a[contains(@href, '/men/mens') and text()='View All']")
    private WebElement viewAllCategories;

    @FindBy(className = "qa-non-link-label")
    private WebElement mensClothesTitle;

    @FindBy(css = "img.img-responsive.product-tile-image._image_l3zrmt")
    private List<WebElement> productItems;

    @FindBy(css = "div.dropdown-toggle")
    private WebElement dropdownSizeToggle;

    @FindBy(css = "ul.dropdown-menu li:not(.visually-disabled)")
    private List<WebElement> availableSizes;

    @FindBy(css = "div.product-sale-price")
    private WebElement catalogProductPrice;

    @FindBy(name = "addToBag")
    private WebElement addToBagButton;

    @FindBy(xpath = "//h2[text()='Added to bag!']")
    private WebElement successfulAddedToBag;

    @FindBy(name = "viewBag")
    private WebElement viewItemInBagButton;

    @FindBy(css = "span[data-test-cart-item-sale-price]")
    private WebElement cartProductPrice;

    @FindBy(css = "div._icon-container_lm3uk3")
    private WebElement returnItems;

    @FindBy(xpath = "//button[@aria-label='increase']")
    private WebElement addToCartButton;

    @Step("Move mouse to element Mens clothes")
    public void movingToElementMen() {
        wait.until(ExpectedConditions.visibilityOf(menFormMenu));
        new Actions(driver)
                .moveToElement(menFormMenu)
                .pause(Duration.ofSeconds(2))
                .perform();
        wait.until(ExpectedConditions.visibilityOf(viewAllCategories));
    }

    @Step("Select chapter 'View all'")
    public void selectChapterViewAll() {
        wait.until(ExpectedConditions.visibilityOf(viewAllCategories)).click();
    }

    @Step("Get subpage title")
    public String getMensPageTitle() {
        wait.until(ExpectedConditions.visibilityOf(mensClothesTitle));
        return mensClothesTitle.getText();
    }

    @Step("Select first available product and click to it")
    public void selectFirstAvailableProductAndClickToIt() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(productItems));
        } catch (TimeoutException e) {
            closePopUpIfAvailable();
            wait.until(ExpectedConditions.visibilityOfAllElements(productItems));
        }
        if (!productItems.isEmpty()) {
            productItems.get(0).click();
        }
    }

    @Step("Select first available size")
    public void selectFirstAvailableSize() {
        new Actions(driver)
                .scrollToElement(returnItems)
                .pause(Duration.ofSeconds(2))
                .perform();
        wait.until(ExpectedConditions.elementToBeClickable(dropdownSizeToggle));
        dropdownSizeToggle.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(availableSizes));
        if (!availableSizes.isEmpty()) {
            availableSizes.get(0).click();
        } else {
            throw new RuntimeException("No available sizes found");
        }
    }

    @Step("Get catalog price")
    public String getCatalogPrice() {
        return catalogProductPrice.getText();
    }

    @Step("Click button 'Add to bag")
    public void clickToBagButton() {
        addToBagButton.click();
    }

    @Step("Check successful added to bag")
    public String getSuccessfulAddedToBagText() {
        wait.until(ExpectedConditions.visibilityOf(successfulAddedToBag));
        return successfulAddedToBag.getText();
    }

    @Step("Get cart price")
    public String getCartPrice() {
        return wait.until(ExpectedConditions.visibilityOf(cartProductPrice)).getText();
    }

    @Step("Check added item on basket")
    public void clickViewButton() {
        viewItemInBagButton.click();
    }

    @Step("Check that the add to cart button is available")
    public boolean isAddToCartButtonEnabled() {
        return addToCartButton.isEnabled();
    }

    @Step("Click button '+'")
    public void clickAddToCart() {
        addToCartButton.click();
    }

    @Step("Add item until button '+' disabled")
    public int addItemsUntilButtonDisabled() {
        int clickCount = 0;

        while (isAddToCartButtonEnabled()) {
            clickAddToCart();
            clickCount++;
        }
        return clickCount;
    }

    @Step("Select first available product from the catalog")
    public void selectFirstAvailableProductFromCatalog() {
        closePopUpIfAvailable();
        movingToElementMen();
        closePopUpIfAvailable();
        selectChapterViewAll();
        closePopUpIfAvailable();
        selectFirstAvailableProductAndClickToIt();
    }

    @Step("Select a product from the catalog and choose its size")
    public void selectProductAndItsSizeFromCatalog() {
        closePopUpIfAvailable();
        movingToElementMen();
        closePopUpIfAvailable();
        selectChapterViewAll();
        closePopUpIfAvailable();
        selectFirstAvailableProductAndClickToIt();
        closePopUpIfAvailable();
        selectFirstAvailableSize();
    }
}
