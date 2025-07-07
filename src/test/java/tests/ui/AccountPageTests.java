package tests.ui;

import configs.TestPropertiesConfig;
import io.qameta.allure.Severity;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AccountPage;
import pages.HomePage;
import pages.MensClothesPage;
import pages.ShoppingCartPage;

import java.time.Duration;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AccountPageTests extends BaseTest {
    HomePage homePage;
    AccountPage accountPage;
    TestPropertiesConfig config = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    private static final String FIRST_NAME = "Ilya";
    private static final String LAST_NAME = "S";
    private static final String POSTAL_CODE = "07008";
    private static final String MONTH_VALUE = "8";
    private static final String DAY_VALUE = "21";
    private static final String ACCOUNT_SUCCESSFUL_TEXT = "Account created!";
    private static final String ACCOUNT_URL = "/myaccount/real-rewards/account-summary";
    private static final String ACCOUNT_SUCCESSFUL_ENTERED_TEXT = "Ilya's Account";


    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Check create account")
    void createAccountPageTest() throws InterruptedException {
        accountPage = new AccountPage(driver);
        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickCreateAccountButton();
        accountPage.inputEmailField(config.getEmail());
        accountPage.inputFirstNameField(FIRST_NAME);
        accountPage.inputLastNameField(LAST_NAME);
        accountPage.movingToElementSignUpText();
        accountPage.inputPasswordField(config.getPassword());
        accountPage.confirmPasswordField(config.getPassword());
        accountPage.zipCodeField(POSTAL_CODE);
        accountPage.dropdownMonthSelectorByValue(MONTH_VALUE);
        accountPage.dropdownDaySelectorByValue(DAY_VALUE);
        accountPage.movingToElementSubmitButton();
//        createAccountPage.clickCheckboxAEEmails();
//        createAccountPage.clickCheckboxAerieEmails();
        accountPage.clickCheckboxAcceptTerms();
        Thread.sleep(5000);
//        createAccountPage.clickSubmitButton();
//
//        Thread.sleep(3000);
//
//        assertEquals(HOMEPAGE_URL + ACCOUNT_URL, createAccountPage.getCurrentUrl());
//        assertEquals(ACCOUNT_SUCCESSFUL_TEXT, createAccountPage.getSuccessfulCreatedAccountText());
    }

    @Test
    @Severity(CRITICAL)
    @Tag("Smoke")
    @DisplayName("Check 'Sign In' account")
    void signInPageTest() throws InterruptedException {
        accountPage = new AccountPage(driver);
        homePage.getHeader().clickAccountButton();
        homePage.getHeader().clickSignInButton();
        accountPage.inputEmailField(config.getEmail());
        accountPage.inputPasswordField(config.getPassword());
        accountPage.submitSignInButtonClick();

        Thread.sleep(5000);

        assertEquals(ACCOUNT_SUCCESSFUL_ENTERED_TEXT, accountPage.getSuccessfulEnteredAccountText());
    }
}
