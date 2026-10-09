package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchResultsPage extends BasePage {

    @FindBy(css = "[data-testid='search-results']")
    private WebElement searchResultsMessage;

    @FindBy(css = "img[data-test='product-image']")
    private List<WebElement> productImages;

    private static final By NO_SEARCH_RESULTS_MESSAGE =
            By.xpath(
                    "//h1[contains(normalize-space(.), " +
                            "\"Sorry! We couldn't find a match for\")]"
            );

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get search results message")
    public String getSearchResultsMessage() {
        return wait.until(
                ExpectedConditions.visibilityOf(
                        searchResultsMessage
                )
        ).getText();
    }

    @Step("Get number of displayed products")
    public int getDisplayedProductsCount() {

        wait.until(driver ->
                !productImages.isEmpty()
        );

        return productImages.size();
    }

    @Step("Get no search results message")
    public String getNoSearchResultsMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        NO_SEARCH_RESULTS_MESSAGE
                )
        ).getText();
    }

    @Step("Check no search results message is displayed")
    public boolean noSearchResultsMessageIsDisplayed() {

        return !driver.findElements(
                NO_SEARCH_RESULTS_MESSAGE
        ).isEmpty();
    }
}