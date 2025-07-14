package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MensClothesPage extends BasePage {
    WebDriver driver;
    WebDriverWait wait;

    public MensClothesPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[text()='Men']")
    private WebElement menFormMenu;

    @FindBy(xpath = "//a[@href='/us/en/c/men/mens?pagetype=clp']")
    private WebElement viewAllCategories;

    @FindBy(className = "qa-non-link-label")
    private WebElement mensClothesTitle;

    @FindBy(css = "img.img-responsive.product-tile-image._image_l3zrmt")
    private List<WebElement> productItems;

    @FindBy(css = "div.dropdown-toggle")
    private WebElement dropdownSizeToggle;

    @FindBy(css = "ul.dropdown-menu li:not(.visually-disabled)")
    private List<WebElement> availableSizes;

    @FindBy(name = "addToBag")
    private WebElement addToBagButton;

    @FindBy(xpath = "//li[@data-value='0043976372']")
    private WebElement chooseTheSize;

    @FindBy(xpath = "//h2[text()='Added to bag!']")
    private WebElement successfulAddedToBag;

    @FindBy(name = "viewBag")
    private WebElement viewItemInBagButton;

    @FindBy(css = "div._icon-container_lm3uk3")
    private WebElement returnItems;

    @FindBy(xpath = "//button[@aria-label='increase']")
    private WebElement addToCartButton;

    @Step("Move mouse to element Mens clothes")
    public void movingToElementMen() {
        new Actions(driver)
                .moveToElement(menFormMenu)
                .perform();
    }

    @Step("Select chapter 'View all'")
    public void selectChapterViewAll() {
        viewAllCategories.click();
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
            productItems.getFirst().click();
        }
    }

    @Step("Select first available size")
    public void selectFirstAvailableSize() {
        new Actions(driver)
                .scrollToElement(returnItems)
                .perform();
        wait.until(ExpectedConditions.elementToBeClickable(dropdownSizeToggle));
        dropdownSizeToggle.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(availableSizes));
        if (!availableSizes.isEmpty()) {
            availableSizes.getFirst().click();
        } else {
            throw new RuntimeException("No available sizes found");
        }
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

    @Step("Check added item on basket")
    public void clickViewButton() {
        viewItemInBagButton.click();
    }

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

    @Step("Select a product from the catalog and choose its size")
    public void selectProductAndItsSizeFromCatalog() {
        movingToElementMen();
        selectChapterViewAll();
        selectFirstAvailableProductAndClickToIt();
        selectFirstAvailableSize();
    }
}
