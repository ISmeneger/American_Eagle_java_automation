package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    @FindBy(css = "button[aria-label='dismiss cookie message']")
    private WebElement cookieButton;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );

        PageFactory.initElements(driver, this);
    }

    @Step("Get current url")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Close pop-up if present")
    public boolean closePopUpIfPresent() {

        By shadowHostLocator =
                By.cssSelector("div.bloomreach-weblayer");

        By closeButtonLocator =
                By.cssSelector(
                        "button.close-button[aria-label='Close'], " +
                                "button.close[aria-label='Close']"
                );

        try {
            List<WebElement> hosts =
                    driver.findElements(shadowHostLocator);

            if (hosts.isEmpty()) {
                return false;
            }

            WebElement shadowHost = hosts.get(0);
            SearchContext shadowRoot =
                    shadowHost.getShadowRoot();

            WebElement closeButton =
                    shadowRoot.findElement(closeButtonLocator);

            if (closeButton.isDisplayed()
                    && closeButton.isEnabled()) {

                closeButton.click();
                return true;
            }

        } catch (NoSuchElementException
                 | NoSuchShadowRootException
                 | StaleElementReferenceException
                 | ElementClickInterceptedException e) {

            return false;
        }

        return false;
    }

    @Step("Close pop-up if available")
    public void closePopUpIfAvailable() {

        By shadowHostLocator =
                By.cssSelector("div.bloomreach-weblayer");

        By closeButtonLocator =
                By.cssSelector(
                        "button.close-button[aria-label='Close'], " +
                                "button.close[aria-label='Close']"
                );

        WebDriverWait popupWait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            popupWait.until(webDriver -> {

                try {
                    // Находим Shadow Host в основном DOM
                    WebElement shadowHost =
                            webDriver.findElement(shadowHostLocator);

                    // Переходим внутрь Shadow DOM
                    SearchContext shadowRoot =
                            shadowHost.getShadowRoot();

                    // Ищем кнопку закрытия уже внутри Shadow DOM
                    WebElement closeButton =
                            shadowRoot.findElement(closeButtonLocator);

                    if (closeButton.isDisplayed()
                            && closeButton.isEnabled()) {

                        closeButton.click();
                        return true;
                    }

                } catch (NoSuchElementException
                         | NoSuchShadowRootException
                         | StaleElementReferenceException
                         | ElementClickInterceptedException e) {

                    return false;
                }

                return false;
            });

            System.out.println("Email pop-up closed successfully.");

        } catch (TimeoutException e) {

            System.out.println(
                    "Email pop-up was not found or could not be closed."
            );
        }
    }

    @Step("Accept cookies if available")
    public void acceptCookiesIfAvailable() {

        try {
            WebDriverWait cookieWait =
                    new WebDriverWait(driver, Duration.ofSeconds(2));

            cookieWait.until(
                    ExpectedConditions.elementToBeClickable(
                            cookieButton
                    )
            ).click();

        } catch (TimeoutException |
                 NoSuchElementException |
                 StaleElementReferenceException e) {

            // Cookie banner is not displayed.
            // Continue the test.
        }
    }

    @Step("Close blocking overlays if available")
    public void closeBlockingOverlaysIfAvailable() {
        acceptCookiesIfAvailable();
        closePopUpIfAvailable();
    }
}