package pages;

import components.FooterComponent;
import components.HeaderComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static constants.CommonConstants.BASE_URL;

public class HomePage extends BasePage {

    @Getter
    private final FooterComponent footer;
    @Getter
    private final HeaderComponent header;

    @FindBy(xpath = "//img[@src='https://s7d2.scene7.com/is/image/aeo/flag__US?fmt=png-alpha&fit=crop&wid=100&qlt=90,0']")
    private WebElement chooseCountry;


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
        chooseCountry.click();
    }

}
