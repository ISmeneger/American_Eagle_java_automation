package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchShadowRootException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    @Step("Get current URL")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Close email pop-up if available")
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
                    WebElement shadowHost =
                            webDriver.findElement(shadowHostLocator);

                    SearchContext shadowRoot =
                            shadowHost.getShadowRoot();

                    WebElement closeButton =
                            shadowRoot.findElement(closeButtonLocator);

                    if (closeButton.isDisplayed()
                            && closeButton.isEnabled()) {

                        closeButton.click();
                        return true;
                    }

                } catch (NoSuchElementException |
                         NoSuchShadowRootException |
                         StaleElementReferenceException |
                         ElementClickInterceptedException ignored) {

                    return false;
                }

                return false;
            });

        } catch (TimeoutException ignored) {
            // Email pop-up is optional.
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
                 StaleElementReferenceException ignored) {

            // Cookie banner is optional.
        }
    }

    @Step("Close blocking overlays if available")
    public void closeBlockingOverlaysIfAvailable() {
        acceptCookiesIfAvailable();
        closePopUpIfAvailable();
    }
}