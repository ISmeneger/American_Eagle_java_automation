package components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HeaderComponent {

    private final WebDriverWait wait;

    public HeaderComponent(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@title='Shop AE']")
    private WebElement subTitleAeoLogo;

    @FindBy(xpath = "//li[@data-test='top-link-wrapper']//button[contains(@class, 'link_EZ5lj')]")
    private WebElement featuredOffersMenu;

    @FindBy(css = "button[aria-label='New']")
    private WebElement newFormMenu;

    @FindBy(xpath = "//a[text()='Women']")
    private WebElement womenFormMenu;

    @FindBy(xpath = "//a[text()='Men']")
    private WebElement menFormMenu;

    @FindBy(css = "a[href*='/x/jeans']")
    private WebElement jeansFormMenu;

    @FindBy(css = "li[data-test='top-link-wrapper'] > a[href*='/c/aerie/']")
    private WebElement aerieFormMenu;

    @FindBy(xpath = "//a[contains(@href, '/x/clearance')]")
    private WebElement clearanceFormMenu;

    @FindBy(name = "search-cta")
    private WebElement searchButton;

    // The SVG is used for visibility checks; click its parent interactive element.

    @FindBy(css = "svg[data-testid='icon-account']")
    private WebElement iconAccount;

    private static final By ACCOUNT_ICON =
            By.cssSelector("svg[data-testid='icon-account']");

    private static final By SEARCH_INPUT =
            By.cssSelector("input[name='search']");

    @FindBy(css = "a[data-testid='sign-in-link']")
    private WebElement signInButton;

    @FindBy(css = "h2.wc-side-tray__title")
    private WebElement modalAccountText;

    @FindBy(css = "a[data-testid='register-link']")
    private WebElement createAccountButton;

    @FindBy(css = "svg[data-testid='icon-favorites']")
    private WebElement favoritesButton;

    @FindBy(xpath = "//h1[text()='Favorites']")
    private WebElement favoritesText;

    @FindBy(css = "a.qa-tnav-bag-icon")
    private WebElement basketButton;

    @FindBy(xpath = "//h1[text()='Shopping Bag']")
    private WebElement basketText;

    @Step("Get text in logo link")
    public String getAeoLogoSubtitleText() {
        return wait.until(
                ExpectedConditions.visibilityOf(subTitleAeoLogo)
        ).getText();
    }

    @Step("Get main logo title")
    public String getAeoLogoTitle() {
        return wait.until(
                ExpectedConditions.visibilityOf(subTitleAeoLogo)
        ).getDomProperty("title");
    }

    @Step("Get text in 'Today's Offers' button on main menu")
    public String getAeoFeaturedOffersText() {
        return wait.until(
                ExpectedConditions.visibilityOf(featuredOffersMenu)
        ).getText();
    }

    @Step("Get text in 'New' tab on main menu")
    public String getAeoNewTabText() {
        return wait.until(
                ExpectedConditions.visibilityOf(newFormMenu)
        ).getText();
    }

    @Step("Get text in 'Women' tab on main menu")
    public String getAeoWomenTabText() {
        return wait.until(
                ExpectedConditions.visibilityOf(womenFormMenu)
        ).getText();
    }

    @Step("Get text in 'Men' tab on main menu")
    public String getAeoMenTabText() {
        return wait.until(
                ExpectedConditions.visibilityOf(menFormMenu)
        ).getText();
    }

    @Step("Get text in 'Jeans' tab on main menu")
    public String getAeoJeansTabText() {
        return wait.until(
                ExpectedConditions.visibilityOf(jeansFormMenu)
        ).getText();
    }

    @Step("Get text in 'Aerie' tab on main menu")
    public String getAeoAerieTabText() {
        return wait.until(
                ExpectedConditions.visibilityOf(aerieFormMenu)
        ).getText();
    }

    @Step("Get text in 'Clearance' tab on main menu")
    public String getAeoClearanceTabText() {
        return wait.until(
                ExpectedConditions.visibilityOf(clearanceFormMenu)
        ).getText();
    }

    @Step("Check 'Search' button is displayed")
    public boolean searchButtonIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(searchButton)
        ).isDisplayed();
    }

    @Step("Click 'Search' button")
    public void clickSearchButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(searchButton)
        ).click();
    }

    @Step("Check search input field is displayed")
    public boolean searchInputIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        SEARCH_INPUT
                )
        ).isDisplayed();
    }

    @Step("Enter search query: {query}")
    public void enterSearchQuery(String query) {

        WebElement searchInput = getVisibleSearchInput();

        searchInput.click();
        searchInput.sendKeys(query);

        wait.until(driver ->
                query.equalsIgnoreCase(
                        searchInput
                                .getDomProperty("value")
                                .trim()
                )
        );
    }

    @Step("Select search suggestion: {query}")
    public void selectSearchSuggestion(String query) {

        By suggestionLocator = By.cssSelector(
                "button[name='select-suggestion'][aria-label='search for "
                        + query
                        + " instead']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        suggestionLocator
                )
        ).click();
    }

    @Step("Check 'Account' icon is displayed")
    public boolean accountButtonIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(iconAccount)
        ).isDisplayed();
    }

    @Step("Click 'Account' button")
    public void clickAccountButton() {

        WebElement accountIcon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        ACCOUNT_ICON
                )
        );

        WebElement clickableAccountButton =
                accountIcon.findElement(By.xpath("./ancestor::*[self::button or self::a][1]"));

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        clickableAccountButton
                )
        ).click();
    }

    @Step("Click 'Create Account' button")
    public void clickCreateAccountButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        createAccountButton
                )
        ).click();
    }

    @Step("Check 'Sign In' button is displayed")
    public boolean signInButtonIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(signInButton)
        ).isDisplayed();
    }

    @Step("Get text in account side tray")
    public String getAccountTitleText() {
        return wait.until(
                ExpectedConditions.visibilityOf(modalAccountText)
        ).getText();
    }

    @Step("Check 'Create Account' button is displayed")
    public boolean createAccountButtonIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(createAccountButton)
        ).isDisplayed();
    }

    @Step("Check 'Favorites' button is displayed")
    public boolean favoritesButtonIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(favoritesButton)
        ).isDisplayed();
    }

    @Step("Click 'Favorites' button")
    public void clickFavoritesButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        favoritesButton
                )
        ).click();
    }

    @Step("Get 'Favorites' page title")
    public String getFavoritesTitleText() {
        return wait.until(
                ExpectedConditions.visibilityOf(favoritesText)
        ).getText();
    }

    @Step("Check 'Basket' button is displayed")
    public boolean basketButtonIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(basketButton)
        ).isDisplayed();
    }

    @Step("Click 'Basket' button")
    public void clickBasketButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        basketButton
                )
        ).click();
    }

    @Step("Get text on basket page")
    public String getBasketTitleText() {
        return wait.until(
                ExpectedConditions.visibilityOf(basketText)
        ).getText();
    }

    @Step("Click 'Sign In' button")
    public void clickSignInButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        signInButton
                )
        ).click();
    }

    private WebElement getVisibleSearchInput() {

        return wait.until(
                        ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                SEARCH_INPUT
                        )
                )
                .stream()
                .filter(WebElement::isEnabled)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException(
                                "Visible and enabled search input was not found"
                        )
                );
    }

    @Step("Submit search query")
    public void submitSearchQuery() {

        WebElement searchInput = getVisibleSearchInput();

        searchInput.sendKeys(Keys.ENTER);
    }
}