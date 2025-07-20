package dto;

import lombok.Data;

@Data
public class BagItem {
    private String itemId;
    private String sku;
    private int quantity;
    private String productName;
    private String size;
    private double price;
    private double originalPrice;
}
