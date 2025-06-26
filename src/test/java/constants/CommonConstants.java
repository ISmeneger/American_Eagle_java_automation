package constants;

import models.Item;

public class CommonConstants {
    public static final String UI_BASE_URL = "https://www.ae.com/us/en";
    public static final String API_BASE_URL = "https://www.ae.com/ugp-api/bag/";

    public static final Item ADD_PRODUCT = Item.builder()
            .skuId("0043222959")
            .quantity(1)
            .build();
}
