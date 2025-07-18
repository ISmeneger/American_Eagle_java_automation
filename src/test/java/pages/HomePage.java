package pages;

import components.FooterComponent;
import components.HeaderComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static constants.CommonConstants.BASE_URL;

public class HomePage extends BasePage {

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
}
