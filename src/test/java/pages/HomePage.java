package pages;

import components.FooterComponent;
import components.HeaderComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static constants.CommonConstants.BASE_URL;

public class HomePage extends BasePage {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @Getter
    private final FooterComponent footer;
    @Getter
    private final HeaderComponent header;

    @FindBy(xpath = "//b[contains(@data-test-shipping-text, '') and contains(., 'Ship to')]")
    private List<WebElement> chooseCountry;


    public HomePage(WebDriver driver) {
        super(driver);
        header = new HeaderComponent(driver);
        footer = new FooterComponent(driver);
        open();
    }

    @Step("Open homepage")
    private void open() {
        driver.get(BASE_URL);
    }

    @Step("Get web title")
    public String getWebTitle() {
        return driver.getTitle();
    }

    @Step("Choose country sale")
    public void chooseCountrySale() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElements(chooseCountry));
        chooseCountry.get(0).click();
    }
}
