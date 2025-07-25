package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected static WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(xpath = "//div[@class='bloomreach-weblayer']")
    private WebElement shadowRoot;

    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @Step("Get current url")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Сatch and close pop-up")
    public void closePopUpIfAvailable() {
        try {
            SearchContext popUp = shadowRoot.getShadowRoot();
            WebElement closePopUp = popUp.findElement(By.cssSelector("button[class='close']"));
            closePopUp.click();
        } catch (org.openqa.selenium.NoSuchElementException e) {

        } catch (Exception e) {

        }
    }
}
