package steps;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class AllureSteps {

    private final WebDriver driver;

    public AllureSteps(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Capture screenshot on test failure")
    public void attachScreenshot() {
        byte[] screenshot =
                ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                "Screenshot on failure",
                new ByteArrayInputStream(screenshot)
        );
    }

    @Step("Attach current URL on test failure")
    public void attachCurrentUrl() {
        Allure.addAttachment(
                "Current URL",
                "text/plain",
                driver.getCurrentUrl()
        );
    }

    @Step("Attach page source on test failure")
    public void attachPageSource() {
        Allure.addAttachment(
                "Page Source",
                "text/html",
                driver.getPageSource(),
                ".html"
        );
    }
}