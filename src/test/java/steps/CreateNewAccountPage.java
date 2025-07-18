package steps;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pages.AccountPage;
import pages.BasePage;

@Getter
public class CreateNewAccountPage extends BasePage {
    static WebDriver driver;

    public CreateNewAccountPage(WebDriver driver) {
        super(driver);
        CreateNewAccountPage.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Create new user in account")
    public void createNewAccount(String email, String firstName, String lastName, String password) {
        AccountPage accountPage = new AccountPage(driver);
        accountPage.inputEmailField(email);
        accountPage.inputFirstNameField(firstName);
        accountPage.inputLastNameField(lastName);
        accountPage.inputPasswordField(password);
        accountPage.confirmPasswordField(password);
    }
}
