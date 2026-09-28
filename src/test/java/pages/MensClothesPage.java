package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MensClothesPage extends BasePage {

    private final WebDriver driver;

    @FindBy(xpath = "//a[text()='Men']")
    private WebElement menFormMenu;

    @FindBy(xpath = "//a[contains(@href, '/men/mens') and text()='View All']")
    private WebElement viewAllCategories;

    @FindBy(css = "[data-testid='page-title'] h1")
    private WebElement mensClothesTitle;

    @FindBy(css = "img[data-test='product-image']")
    private List<WebElement> productItems;

    public MensClothesPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Move mouse to Men's clothes menu")
    public void movingToElementMen() {

        WebElement menMenu = wait.until(
                ExpectedConditions.visibilityOf(menFormMenu)
        );

        new Actions(driver)
                .scrollToElement(menMenu)
                .moveToElement(menMenu)
                .perform();

        wait.until(
                ExpectedConditions.visibilityOf(viewAllCategories)
        );
    }

    @Step("Select chapter 'View All'")
    public void selectChapterViewAll() {

        WebElement viewAll = wait.until(
                ExpectedConditions.elementToBeClickable(viewAllCategories)
        );

        new Actions(driver)
                .moveToElement(viewAll)
                .perform();

        viewAll.click();
    }

    @Step("Get Men's Clothes page title")
    public String getMensPageTitle() {
        return wait.until(
                ExpectedConditions.visibilityOf(mensClothesTitle)
        ).getText();
    }

    @Step("Select first available product and open it")
    public void selectFirstAvailableProductAndClickToIt() {

        try {
            wait.until(
                    ExpectedConditions.visibilityOfAllElements(productItems)
            );
        } catch (TimeoutException e) {
            closePopUpIfAvailable();

            wait.until(
                    ExpectedConditions.visibilityOfAllElements(productItems)
            );
        }

        if (productItems.isEmpty()) {
            throw new IllegalStateException(
                    "No products found in Men's catalog"
            );
        }

        WebElement firstProduct = wait.until(
                ExpectedConditions.elementToBeClickable(
                        productItems.get(0)
                )
        );

        firstProduct.click();
    }
}