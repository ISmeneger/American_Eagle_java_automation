package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pages.MensClothesPage;

public class ProductCatalogSteps {

    private final MensClothesPage mensClothesPage;

    public ProductCatalogSteps(WebDriver driver) {
        this.mensClothesPage = new MensClothesPage(driver);
    }

    @Step("Open first available product from Men's catalog")
    public void openFirstAvailableMensProduct() {

        mensClothesPage.closePopUpIfPresent();

        mensClothesPage.movingToElementMen();

        mensClothesPage.closePopUpIfPresent();

        mensClothesPage.selectChapterViewAll();

        mensClothesPage.closePopUpIfAvailable();

        mensClothesPage.selectFirstAvailableProductAndClickToIt();
    }
}