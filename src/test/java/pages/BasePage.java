package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePage {
    WebDriver driver;
    private static final Logger log = LoggerFactory.getLogger(BasePage.class);


    @FindBy(xpath = "//a[@href='/us/en' and @class = 'xm-link-to qa-xm-link-to  logo-link _logo-link-aeo_1aiaa9']")
    private WebElement title;

    @FindBy(xpath = "//div[@class='bloomreach-weblayer']")
    private WebElement shadowRoot;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Getting current url")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Get subpage title")
    public String getTitle() {
        return driver.getTitle();
    }

    @Step("Сatch and close pop-up")
    public void closePopUp() {
        try {
            // Получение shadow root и закрытие поп-апа
            SearchContext popUp = shadowRoot.getShadowRoot();
            WebElement closePopUp = popUp.findElement(By.cssSelector("button[class='close']"));
            closePopUp.click();

            log.info("Поп-ап закрыт успешно.");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            log.info("Поп-ап не найден.");
        } catch (Exception e) {
            log.error("Ошибка при закрытии поп-апа: ", e);
        }
    }
}
