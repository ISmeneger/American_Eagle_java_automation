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

import static constants.CommonConstants.BASE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MensClothesPage extends BasePage {
    WebDriver driver;
    WebDriverWait wait;

    public MensClothesPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[text()='Men']")
    WebElement menFormMenu;

    @FindBy(xpath = "//a[@href='/us/en/c/men/mens?pagetype=clp']")
    WebElement viewAllCategories;

    @FindBy(className = "qa-non-link-label")
    WebElement mensClothesTitle;

    @FindBy(css = "img.img-responsive.product-tile-image._image_l3zrmt")
    WebElement mensTShirtBlack;

    @FindBy(name = "addToBag")
    WebElement addToBagButton;

    @FindBy(xpath = "//li[@data-value='0043976372']")
    WebElement chooseTheSize;

    @FindBy(xpath = "//h2[text()='Added to bag!']")
    WebElement successfulAddedToBag;

    @FindBy(name = "viewBag")
    WebElement viewItemInBagButton;

    @Step("Move mouse to element Mens clothes")
    public void movingToElementMen() {
        new Actions(driver)
                .moveToElement(menFormMenu)
                .perform();
    }

    @Step("Choose chapter View all")
    public void chooseChapterViewAll() {
        viewAllCategories.click();
    }

    @Step("Get subpage title")
    public String getMensPageTitle() {
        return mensClothesTitle.getText();
    }

    @Step("Move mouse to element T-Shirt Black")
    public void movingToElementTShirt() {
        new Actions(driver)
                .moveToElement(mensTShirtBlack)
                .perform();
    }

    @Step("Choose T-Shirt in catalogue")
    public void chooseTShirtInCatalogue() {
        mensTShirtBlack.click();
    }

    @Step("Check button 'Add to bag")
    public void clickToBagButton() {
        addToBagButton.click();
    }

    @Step("Choose the size")
    public void chooseTheSizeS() {
        chooseTheSize.click();
    }

    @Step("Check successful added to bag")
    public String getSuccessfulAddedToBagText() {
        return successfulAddedToBag.getText();
    }

    @Step("Check added item on basket")
    public void clickViewButton() {
        viewItemInBagButton.click();
    }
}
