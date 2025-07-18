package steps;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.BasePage;

import java.io.ByteArrayInputStream;

public class AllureSteps {
    @Step("Capture screenshot (extension)")
    public void captureScreenshotSpoiler() {
        Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) BasePage.getDriver())
                .getScreenshotAs(OutputType.BYTES)));
    }

    @Step("save HTML in report")
    public void attachPageSourceForAllure(String name) {
        String pageSource = BasePage.getDriver().getPageSource();
        Allure.addAttachment(name, "text/html", pageSource, ".html");
    }

}
