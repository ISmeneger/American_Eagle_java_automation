package utils;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import steps.AllureSteps;

import java.util.function.Supplier;

public class AllureExtension implements AfterTestExecutionCallback {

    private final Supplier<WebDriver> driverSupplier;

    public AllureExtension(Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {

        if (context.getExecutionException().isEmpty()) {
            return;
        }

        WebDriver driver = driverSupplier.get();

        if (driver == null) {
            return;
        }

        AllureSteps allureSteps = new AllureSteps(driver);

        allureSteps.attachScreenshot();
        allureSteps.attachCurrentUrl();
        allureSteps.attachPageSource();
    }
}