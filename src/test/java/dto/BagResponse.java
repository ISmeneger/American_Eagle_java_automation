package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BagResponse {

    private BagData data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class BagData {

        private String id;
        private String currencyCode;
        private Summary summary;
        private List<Item> items;
        private int itemCount;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Item {

        private String itemId;
        private String productId;
        private String productName;
        private String size;
        private String color;
        private String sku;
        private int quantity;
        private double originalPrice;
        private double price;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Summary {

        private String id;
        private double shipping;
        private double shippingTax;
        private double subtotal;
        private double discount;
        private double donation;
        private double subtotalMinusDiscount;
        private double tax;
        private double shippingItemsCost;
        private double pickupItemsCost;
        private double total;
        private double amountUntilFreeShipping;
        private double freeShippingThreshold;
        private double giftCardTotal;
        private double giftCardStandardTotal;
        private double giftCardInstantCreditTotal;

        private List<AppliedPromotion> appliedPromotions;
        private OrderSummarySavings orderSummarySavings;
        private double creditSavingsAmount;
        private double netTotal;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class AppliedPromotion {

        private String id;
        private String name;
        private String message;
        private double discount;
        private boolean qualified;
        private int type;
        private String discountType;
        private int channel;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class OrderSummarySavings {

        private double listPriceTotal;
        private double saleMarkdown;
        private double discounts;
        private double rawShipping;
        private double subTotalBeforeDiscount;
        private double savings;
    }
}