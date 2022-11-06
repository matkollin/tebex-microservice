package at.matkollin.service.tebex.controller;

import at.matkollin.service.tebex.dto.webhook.PaymentDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WebhookController.class)
public class WebhookControllerTest {

  @InjectMocks
  private WebhookController webhookController;

  @Test
  @DisplayName("Test webhook")
  public void testWebhook() throws Exception {
    PaymentDTO paymentDTO = PaymentDTO.builder()
            .transactionId("123")
            .status(PaymentDTO.Status.builder()
                    .id(1)
                    .description("unknown")
                    .build())
            .paymentSequence("1")
            .createdAt("2020-01-01")
            .price(PaymentDTO.Price.builder()
                    .amount(1.0)
                    .currency("EUR")
                    .build())
            .customer(PaymentDTO.Customer.builder()
                    .ip("127.0.0.1")
                    .country("AT")
                    .email("test@gmail.com")
                    .firstName("Max")
                    .lastName("Mustermann")
                    .marketingConsent(true)
                    .postalCode("1234")
                    .username(PaymentDTO.Customer.Username.builder()
                            .username("test")
                            .id(1)
                            .build())
                    .postalCode("1234")
                    .build())
            .build();

    this.webhookController.webhook(paymentDTO);
  }

}
