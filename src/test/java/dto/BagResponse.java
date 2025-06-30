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
        public Summary summary;
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
        public String id;
        public double shipping;
        public double shippingTax;
        public double subtotal;
        public double discount;
        public double donation;
        public double subtotalMinusDiscount;
        public double tax;
        public double shippingItemsCost;
        public double pickupItemsCost;
        public double total;
        public double amountUntilFreeShipping;
        public double freeShippingThreshold;
        public double giftCardTotal;
        public double giftCardStandardTotal;
        public double giftCardInstantCreditTotal;

        public List<AppliedPromotion> appliedPromotions;
        public OrderSummarySavings orderSummarySavings;
        public double creditSavingsAmount;
        public double netTotal;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class AppliedPromotion {
        public String id;
        public String name;
        public String message;
        public double discount;
        public boolean qualified;
        public int type;
        public String discountType;
        public int channel;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class OrderSummarySavings {
        public double listPriceTotal;
        public double saleMarkdown;
        public double discounts;
        public double rawShipping;
        public double subTotalBeforeDiscount;
        public double savings;
    }
}
