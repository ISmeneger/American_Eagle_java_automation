package tests.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ShoppingCartPage;
import pages.HomePage;
import pages.MensClothesPage;

import java.time.Duration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static tests.ui.HomePageTests.HOMEPAGE_URL;


class MensClothesChapterTests extends BaseTest {
    HomePage homePage;
    MensClothesPage mensClothesPage;
    ShoppingCartPage cartPage;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    private static final String CURRENT_MEN_URL = "/c/men/mens?pagetype=clp";
    private static final String CURRENT_ITEM_URL = "/p/men/graphic-tops/destination-graphics/ae-boxy-destination-graphic-t-shirt/0160_1828_001?menu=cat4840004";
    private static final String CURRENT_CART_URL = "/cart";
    private static final String SUB_TITLE_TEXT = "Men's Clothes";
    private static final String SUCCESSFUL_ADDED_TO_BAG = "Added to bag!";
    private static final String CART_TITLE_UNAUTHORIZED = "Shopping Bag";
    private static final String CART_TITLE_AUTHORIZED = "Ilya's Bag";
    private static final String QUANTITY_OF_ITEMS = "1 item";
    private static final String ONE_ITEM_COST = "$26.95";
    private static final String TWO_ITEM_COST = "$53.90";
    private static final String PRODUCT_NAME = "AE Boxy Destination Graphic T-Shirt";
    private static final String QUANTITY_OF_ITEMS_AFTER_UPDATE = "2 item";
    private static final String SHIPPING_COST = "$7.95";
    private static final String SUB_TOTAL_COST = "$61.85";

    @BeforeEach
    void setupPage() {
        homePage = new HomePage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Check Mens clothes page")
    void checkMensFormPageTest() throws InterruptedException {
        mensClothesPage = new MensClothesPage(driver);
        mensClothesPage.movingToElementMen();
        mensClothesPage.chooseChapterViewAll();

        Thread.sleep(3000);
        assertEquals(HOMEPAGE_URL + CURRENT_MEN_URL, mensClothesPage.getCurrentUrl());
        assertEquals(SUB_TITLE_TEXT, mensClothesPage.getMensPageTitle());
    }

    @Test
    @DisplayName("Check T-Shirt from catalogue and add in to cart")
    void checkMensTShirtBlackTest() {
        mensClothesPage = new MensClothesPage(driver);
        mensClothesPage.movingToElementMen();
        mensClothesPage.chooseChapterViewAll();
        mensClothesPage.movingToElementTShirt();
        mensClothesPage.closePopUp();
        mensClothesPage.chooseTShirtInCatalogue();
        mensClothesPage.clickToBagButton();
        mensClothesPage.chooseTheSizeS();

        assertEquals(HOMEPAGE_URL + CURRENT_ITEM_URL, mensClothesPage.getCurrentUrl());
        assertEquals(SUCCESSFUL_ADDED_TO_BAG, mensClothesPage.getSuccessfulAddedToBagText());
    }

    @Test
    @DisplayName("Check if an unauthorized user added a product to the cart")
    void addItemToBagUnauthorizedUserTest() throws InterruptedException {
        mensClothesPage = new MensClothesPage(driver);
        mensClothesPage.movingToElementMen();
        mensClothesPage.chooseChapterViewAll();
        mensClothesPage.movingToElementTShirt();
        mensClothesPage.closePopUp();
        mensClothesPage.chooseTShirtInCatalogue();
        mensClothesPage.clickToBagButton();
        mensClothesPage.chooseTheSizeS();
        mensClothesPage.clickViewButton();

        cartPage = new ShoppingCartPage(driver);

        Thread.sleep(3000);
        assertAll(
                () -> assertEquals(HOMEPAGE_URL + CURRENT_CART_URL, cartPage.getCurrentUrl()),
                () -> assertEquals(CART_TITLE_UNAUTHORIZED, cartPage.getCartTitleText()),
//                () -> assertTrue(cartPage.getQuantityOfItemsText().contains(QUANTITY_OF_ITEMS)),
                () -> assertEquals(PRODUCT_NAME, cartPage.getProductName()),
                () -> assertEquals(ONE_ITEM_COST, cartPage.getItemCostInCart())
                );

        cartPage.editItemButton();
        cartPage.movingToElementUpdateBagButton();
        cartPage.addItemToBag();
        cartPage.updateBag();

        assertAll(
//                () -> assertTrue(cartPage.getQuantityOfItemsText().contains(QUANTITY_OF_ITEMS_AFTER_UPDATE)),
//                () -> assertEquals(TWO_ITEM_COST, cartPage.getItemCostInCart()),
                () -> assertEquals(SHIPPING_COST, cartPage.getShippingCostValue()),
                () -> assertEquals(SUB_TOTAL_COST, cartPage.getSubTotalCostValue())
                );
    }

    @Test
    @DisplayName("Check if an authorized user has added a product to the cart")
    void checkAuthorizedUserMensTShirtBlackInCartTest() throws InterruptedException {
        mensClothesPage = new MensClothesPage(driver);
        mensClothesPage.movingToElementMen();
        mensClothesPage.chooseChapterViewAll();
        mensClothesPage.movingToElementTShirt();
        mensClothesPage.closePopUp();
        mensClothesPage.chooseTShirtInCatalogue();
        mensClothesPage.clickToBagButton();
        mensClothesPage.chooseTheSizeS();
        mensClothesPage.clickViewButton();

        cartPage = new ShoppingCartPage(driver);



        Thread.sleep(3000);
        assertEquals(HOMEPAGE_URL + CURRENT_CART_URL, cartPage.getCurrentUrl());
        assertEquals(CART_TITLE_AUTHORIZED, cartPage.getCartTitleAuthorizedUserText());
    }
}
