package tests.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.AllureExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;

    @RegisterExtension
    private final AllureExtension allureExtension =
            new AllureExtension(() -> driver);

    @BeforeEach
    void setup() {
        initDriver();

        if (isRemoteRun()) {
            driver.manage().window().fullscreen();
        } else {
            driver.manage().window().maximize();
        }

        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(0));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void initDriver() {

        String remoteUrl =
                System.getenv("SELENIUM_REMOTE_URL");

        ChromeOptions options =
                createChromeOptions();

        if (isRemoteRun()) {
            try {
                driver = new RemoteWebDriver(
                        new URL(remoteUrl),
                        options
                );
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException(
                        "Invalid Selenium Remote WebDriver URL: " + remoteUrl,
                        e
                );
            }
        } else {
            driver = new ChromeDriver(options);
        }
    }

    private ChromeOptions createChromeOptions() {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");

        options.setExperimentalOption(
                "prefs",
                Map.of(
                        "profile.default_content_setting_values.geolocation", 2,
                        "profile.default_content_setting_values.notifications", 2
                )
        );

        if (isRemoteRun()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            options.setCapability(
                    "goog:loggingPrefs",
                    Map.of("browser", "ALL")
            );
        }

        return options;
    }

    private boolean isRemoteRun() {
        String remoteUrl =
                System.getenv("SELENIUM_REMOTE_URL");

        return remoteUrl != null
                && !remoteUrl.isBlank();
    }
}