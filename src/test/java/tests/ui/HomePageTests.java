package tests.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomePageTests extends BaseTest {
    HomePage homePage;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Check HomePage url")
    void getHomePageUrlTest() {
        String actualUrl = "https://www.ae.com/us/en";

        assertEquals(actualUrl, homePage.getCurrentUrl());
    }

    @Test
    @DisplayName("Check HomePage title")
    void openHomePageTest() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ember51")));
        homePage.chooseCountrySale();
        String actualTitle = "Men’s & Women’s Jeans, Clothes & Accessories | American Eagle";

        assertEquals(actualTitle, homePage.getTitle());
    }
}
