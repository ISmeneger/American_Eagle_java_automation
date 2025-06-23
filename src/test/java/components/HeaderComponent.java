package components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent {
    WebDriver driver;

    @FindBy(xpath = "//a[@href = '/us/en' and @class = 'xm-link-to qa-xm-link-to  logo-link _logo-link-aeo_1aiaa9']")
    private WebElement subTitleLink;

    public HeaderComponent(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Click to link")
    public void clickSubTitleLink() {
        subTitleLink.click();
    }

    @Step("Getting current url")
    public String getCurrentUrlHomePage() {
        return driver.getCurrentUrl();
    }


}
