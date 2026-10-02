package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pages.AccountPage;

public class RegistrationSteps {

    private final AccountPage accountPage;

    public RegistrationSteps(WebDriver driver) {
        this.accountPage = new AccountPage(driver);
    }

    @Step("Fill registration form for email: {email}")
    public void fillRegistrationForm(
            String email,
            String firstName,
            String lastName,
            String password
    ) {
        accountPage.inputEmailField(email);
        accountPage.inputFirstNameField(firstName);
        accountPage.inputLastNameField(lastName);
        accountPage.inputPasswordField(password);
        accountPage.confirmPasswordField(password);
    }
}