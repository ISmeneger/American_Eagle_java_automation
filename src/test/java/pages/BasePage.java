package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BasePage {
    WebDriver driver;

    @FindBy(xpath = "//a[@href = '/us/en' and @class = 'xm-link-to qa-xm-link-to  logo-link _logo-link-aeo_1aiaa9']")
    private WebElement title;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Getting current url")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Get subpage title")
    public String getTitle() {
        return title.getText();
    }


}
