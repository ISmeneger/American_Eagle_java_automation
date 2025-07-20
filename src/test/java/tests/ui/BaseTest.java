package tests.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AllureExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

class BaseTest {
    static WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    @ExtendWith(AllureExtension.class)
    void setup() {
        initDriver();
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            driver.manage().window().fullscreen();
        } else {
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    @ExtendWith(AllureExtension.class)
    void tearDown() {
        driver.quit();
    }

    private void initDriver() {
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        if (remoteUrl != null && !remoteUrl.isEmpty()) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));
            try {
                driver = new RemoteWebDriver(new URL(remoteUrl), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException("Malformed URL for Selenium Remote WebDriver", e);
            }
        } else {
            driver = new ChromeDriver();
        }
    }
}
