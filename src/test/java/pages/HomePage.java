package pages;

import components.FooterComponent;
import components.HeaderComponent;
import configs.ConfigProvider;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final String UI_BASE_URL =
            ConfigProvider.get().getUiBaseUrl();

    @Getter
    private final FooterComponent footer;
    @Getter
    private final HeaderComponent header;

    public HomePage(WebDriver driver) {
        super(driver);
        header = new HeaderComponent(driver);
        footer = new FooterComponent(driver);
        open();
    }

    @Step("Open homepage")
    private void open() {
        driver.get(UI_BASE_URL);
    }

    @Step("Get web title")
    public String getWebTitle() {
        return driver.getTitle();
    }
}
