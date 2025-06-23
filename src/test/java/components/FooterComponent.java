package components;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FooterComponent {
    WebDriver driver;

    @FindBy(xpath = "//p[@class = 'copyright _no-bottom-margin_ksdzei _footer-copyright_1c0c3k']")
    private WebElement copyrightText;

    @FindBy(id = "un_version")
    private WebElement footerText;

    public FooterComponent(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Get copyright text")
    public String getText() {
        return copyrightText.getText();
    }

    @Step("Scroll to copyright text")
    public void scrollingToElement() {
        new Actions(driver)
                .scrollToElement(footerText)
                .perform();
    }

    @Step("Getting current url")
    public String getCurrentUrlInformationPage() {
        return driver.getCurrentUrl();
    }
}
