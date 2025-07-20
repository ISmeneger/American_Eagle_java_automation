package components;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FooterComponent {
    WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    @FindBy(xpath = "//p[contains(@class, 'copyright')]")
    private WebElement copyrightText;

    @FindBy(xpath = "//img[contains(@src, 'Footer-logos.svg')]")
    private WebElement footerImg;

    public FooterComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Scroll to copyright text")
    public void scrollingToElement() {
        new Actions(driver)
                .scrollToElement(copyrightText)
                .pause(Duration.ofSeconds(2))
                .perform();
    }

    @Step("Get copyright text")
    public String getCopyrightText() {
        return copyrightText.getText();
    }

    @Step("Scroll footer img text")
    public void scrollingToElementFooterImg() {
        new Actions(driver)
                .scrollToElement(footerImg)
                .pause(Duration.ofSeconds(2))
                .perform();
        wait.until(ExpectedConditions.visibilityOf(footerImg));
    }

    @Step("Check footer img is displayed")
    public boolean footerImgIsDisplayed() {
        return footerImg.isDisplayed();
    }
}
