package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BasePage {
    protected static WebDriver driver;
    protected WebDriverWait wait;

    private static final Logger log = LoggerFactory.getLogger(BasePage.class);


    @FindBy(xpath = "//a[@href='/us/en' and @class = 'xm-link-to qa-xm-link-to  logo-link _logo-link-aeo_1aiaa9']")
    private WebElement title;

    @FindBy(xpath = "//div[@class='bloomreach-weblayer']")
    private WebElement shadowRoot;

    @FindBy(css = "button[class='close']")
    private WebElement closePopUp;

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
//            log.info("Поп-ап закрыт успешно.");
        } catch (org.openqa.selenium.NoSuchElementException e) {
//            log.info("Поп-ап не найден.");
        } catch (Exception e) {
//            log.error("Ошибка при закрытии поп-апа: ", e);
        }
    }
}
