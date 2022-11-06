package at.matkollin.service.tebex.dto.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

  @JsonProperty("transaction_id")
  private String transactionId;
  private Status status;
  @JsonProperty("payment_sequence")
  private String paymentSequence;
  @JsonProperty("created_at")
  private String createdAt;
  private Price price;
  private Customer customer;
  private Product[] products;
  private Coupon[] coupons;
  // TODO: Gift Cards
  @Nullable
  @JsonProperty("recurring_payment_reference")
  private RecurringPayment recurringPaymentSubject;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Status {

    private int id;
    private String description;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Price {

    private double amount;
    private String currency;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Customer {

    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private String ip;
    private Username username;
    @JsonProperty("marketing_consent")
    private boolean marketingConsent;
    private String country;
    @JsonProperty("postal_code")
    private String postalCode;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Username {

      private int id;
      private String username;

    }
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Product {

    private int id;
    private String name;
    private int quantity;
    @JsonProperty("base_price")
    private BasePrice basePrice;
    @JsonProperty("paid_price")
    private PaidPrice paidPrice;
    private Variable[] variables;
    @JsonProperty("expires_at")
    private String expiresAt;
    @Nullable
    private String custom;
    private Customer.Username username;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasePrice {

      private int amount;
      private String currency;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaidPrice {

      private int amount;
      private String currency;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Variable {

      private String identifier;
      private int option;

    }
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Coupon {

    private int id;
    private String code;
    private String type;
    @JsonProperty("discount_type")
    private String discountType;
    @JsonProperty("discount_percentage")
    private double discountPercentage;
    @JsonProperty("discount_amount")
    private double discountAmount;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RecurringPayment {

    private String reference;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("next_payment_at")
    private String nextPaymentAt;
    private Status status;
    // TODO: initial_payment
    // TODO: last_payment
    private Price price;
    @JsonProperty("cancelled_at")
    private String cancelledAt;
    @JsonProperty("cancel_reason")
    private String cancelReason;

  }

}
