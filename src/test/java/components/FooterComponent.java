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

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//p[contains(@class, 'copyright')]")
    private WebElement copyrightText;

    @FindBy(xpath = "//img[contains(@src, 'Footer-logos.svg')]")
    private WebElement footerImg;

    public FooterComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );

        PageFactory.initElements(driver, this);
    }

    @Step("Scroll to copyright text")
    public void scrollingToElement() {

        new Actions(driver)
                .scrollToElement(copyrightText)
                .perform();

        wait.until(
                ExpectedConditions.visibilityOf(copyrightText)
        );
    }

    @Step("Get copyright text")
    public String getCopyrightText() {
        return wait.until(
                ExpectedConditions.visibilityOf(copyrightText)
        ).getText();
    }

    @Step("Scroll to footer image")
    public void scrollingToElementFooterImg() {

        new Actions(driver)
                .scrollToElement(footerImg)
                .perform();

        wait.until(
                ExpectedConditions.visibilityOf(footerImg)
        );
    }

    @Step("Check footer image is displayed")
    public boolean footerImgIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(footerImg)
        ).isDisplayed();
    }
}