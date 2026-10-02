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

    @FindBy(css = "p[class*='copyright']")
    private WebElement copyrightText;

    @FindBy(css = "img[src*='Footer-logos.svg']")
    private WebElement footerImage;

    public FooterComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );

        PageFactory.initElements(driver, this);
    }

    @Step("Scroll to copyright text")
    public void scrollToCopyrightText() {

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
    public void scrollToFooterImage() {

        new Actions(driver)
                .scrollToElement(footerImage)
                .perform();

        wait.until(
                ExpectedConditions.visibilityOf(footerImage)
        );
    }

    @Step("Check 'Footer' image is displayed")
    public boolean footerImageIsDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(footerImage)
        ).isDisplayed();
    }
}