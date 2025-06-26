package tests.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import controller.ProductController;
import io.restassured.response.Response;
import models.AddProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static constants.CommonConstants.ADD_PRODUCT;

public class ProductTests {
    @Test
    @JsonIgnoreProperties(ignoreUnknown = true)
    void createProductTest() {
        ProductController productController = new ProductController();

        Response response = productController.createProduct(ADD_PRODUCT);
//        AddProductResponse createdProductResponse = response.as(AddProductResponse.class);

        Assertions.assertEquals(202, response.getStatusCode());
//        Assertions.assertEquals("o12237986403", createdProductResponse.getCartId());
//        Assertions.assertTrue(createdProductResponse.getErrors().isEmpty());
    }
}
