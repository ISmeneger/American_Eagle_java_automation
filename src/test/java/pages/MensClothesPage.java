package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class MensClothesPage extends BasePage {

    public MensClothesPage(WebDriver driver) {
        super(driver);
    }

//    @FindBy(xpath = "//a[@href='/us/en/c/men/mens?pagetype=plp']")
//    WebElement mensForm;

    WebElement mensForm = driver.findElement(By.xpath("//a[@href='/us/en/c/men/mens?pagetype=plp']"));

//    @FindBy(xpath = "//a[@href='/us/en/c/men/mens?pagetype=clp']")
//    WebElement viewAllCategories;

    WebElement viewAllCategories = driver.findElement(By.xpath("//a[@href='/us/en/c/men/mens?pagetype=clp']"));

//    @FindBy(className = "qa-non-link-label _non-link-label_yzxc3z")
//    WebElement mensClothesTitle;

//    WebElement mensClothesTitle = driver.findElement(By.xpath("//div[@class='container-fluid qa-breadcrumb-items _container_yzxc3z']"));

    @Step("Move mouse to element Mens clothes")
    public void movingToElementMen() {
        new Actions(driver)
                .moveToElement(mensForm)
                .perform();
    }

    @Step("Choose chapter View all")
    public void chooseChapterViewAll() {
        viewAllCategories.click();
    }

//    @Step("Get subpage title")
//    public String getSubPageTitle() {
//        return mensClothesTitle.getText();
//    }
}
