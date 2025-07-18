package components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HeaderComponent {
    WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    public HeaderComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@title='Shop AE']")
    private WebElement subTitleAeoLogo;

    @FindBy(xpath = "//li[@data-test='top-link-wrapper']//button[contains(@class, 'link_EZ5lj')]")
    private WebElement featuredOffersMenu;

    @FindBy(xpath = "//a[text()='Women']")
    private WebElement womenFormMenu;

    @FindBy(xpath = "//a[text()='Men']")
    private WebElement menFormMenu;

    @FindBy(xpath = "//a[contains(@href, '/x/jeans') and normalize-space(text())='Jeans']")
    private WebElement jeansFormMenu;

    @FindBy(xpath = "//span[text()='Shoes & Accessories']")
    private WebElement shoesAndAccessFormMenu;

    @FindBy(xpath = "//span[text()='Loungewear & PJs']")
    private WebElement loungewearFormMenu;

    @FindBy(xpath = "//a[normalize-space(text())='Aerie']")
    private WebElement aerieFormMenu;

    @FindBy(xpath = "//a[contains(@href, '/x/clearance')]")
    private WebElement clearanceFormMenu;

    @FindBy(name =  "search-cta")
    private WebElement searchButton;

    @FindBy(xpath = "//input[@name='search']")
    private WebElement searchInputField;

    @FindBy(className = "modal-title")
    private WebElement modalSearchText;

    @FindBy(css = "svg[data-testid='icon-account']")
    private WebElement iconAccount;

    @FindBy(name = "signin")
    private WebElement signInButton;

    @FindBy(className = "modal-title")
    private WebElement modalAccountText;

    @FindBy(xpath = "//a[@data-test='register-button']")
    private WebElement createAccountButton;

    @FindBy(css = "svg[data-testid='icon-favorites']")
    private WebElement favoritesButton;

    @FindBy(xpath = "//h1[text()='Favorites']")
    private WebElement favoritesText;

    @FindBy(css = "a[href='/us/en/cart']")
    private WebElement basketButton;

    @FindBy(xpath = "//h1[text()='Shopping Bag']")
    private WebElement basketText;

    @FindBy(xpath = "//a[@href='/us/en/c/men/mens?pagetype=clp']")
    WebElement viewAllCategories;

    @Step("Get text in logo link")
    public String getAeoLogoSubtitleText() {
        return subTitleAeoLogo.getText();
    }

    @Step("Get main logo title")
    public String getAeoLogoSubtitleValue() {
        return subTitleAeoLogo.getDomProperty("title");
    }

    @Step("Get text in 'Today's Offers' button on main menu")
    public String getAeoFeaturedOffersText() {
        return wait.until(ExpectedConditions.visibilityOf(featuredOffersMenu)).getText();
    }

    @Step("Get text in 'Women' tab on main menu")
    public String getAeoWomenTabText() {
        return womenFormMenu.getText();
    }

    @Step("Get text in 'Men' tab on main menu")
    public String getAeoMenTabText() {
        return menFormMenu.getText();
    }

    @Step("Get text in 'Jeans' tab on main menu")
    public String getAeoJeansTabText() {
        return jeansFormMenu.getText();
    }

    @Step("Get text in 'Shoes & Accessories' tab on main menu")
    public String getAeoShoesAndAccesTabText() {
        return shoesAndAccessFormMenu.getText();
    }

    @Step("Get text in 'Loungewear & PJs' tab on main menu")
    public String getAeoLoungewearTabText() {
        return loungewearFormMenu.getText();
    }

    @Step("Get text in 'Aerie' tab on main menu")
    public String getAeoAerieTabText() {
        return aerieFormMenu.getText();
    }

    @Step("Get text in 'Clearance' tab on main menu")
    public String getAeoClearanceTabText() {
        return clearanceFormMenu.getText();
    }

    @Step("Check 'Search' button is displayed")
    public Boolean searchButtonIsDisplayed() {
        return searchButton.isDisplayed();
    }

    @Step("Click search button")
    public void clickSearchButton() {
        searchButton.click();
    }

    @Step("Get text in modal menu 'Search'")
    public String getSearchTitleText() {
        return modalSearchText.getText();
    }

    @Step("Check search input field is displayed")
    public Boolean searchInputIsDisplayed() {
        return searchInputField.isDisplayed();
    }

    @Step("Check account icon is displayed")
    public Boolean accountButtonIsDisplayed() {
        return iconAccount.isDisplayed();
    }

    @Step("Click account button")
    public void clickAccountButton() {
        iconAccount.click();
    }

    @Step("Click Create account button")
    public void clickCreateAccountButton() {
        createAccountButton.click();
    }

    @Step("Check 'Sign in' button is displayed")
    public Boolean signInButtonIsDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(signInButton)).isDisplayed();
    }

    @Step("Get text in modal menu 'Account'")
    public String getAccountTitleText() {
        return modalAccountText.getText();
    }

    @Step("Check 'Create Account' button is displayed")
    public Boolean createAccountButtonIsDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(createAccountButton)).isDisplayed();
    }

    @Step("Check 'Favorites' button is displayed")
    public boolean favoriteButtonIsDisplayed() {
        return favoritesButton.isDisplayed();
    }

    @Step("Click 'Favorite' button")
    public void clickFavoriteButton() {
        favoritesButton.click();
    }

    @Step("Get text in menu 'Favorites'")
    public String getFavoriteTitleText() {
        return favoritesText.getText();
    }

    @Step("Check basket button is displayed")
    public Boolean basketButtonIsDisplayed() {
        return basketButton.isDisplayed();
    }

    @Step("Click basket button")
    public void clickBasketButton() {
        basketButton.click();
    }

    @Step("Get text on basket page")
    public String getBasketTitleText() {
        return basketText.getText();
    }

    @Step("Click 'Sign In' button")
    public void clickSignInButton() {
        signInButton.click();
    }
}
