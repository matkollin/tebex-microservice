package at.matkollin.service.tebex.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WebhookController.class)
public class WebhookControllerTest {

  @MockBean
  private WebhookController webhookController;

  @Test
  @DisplayName("Test webhook")
  public void testWebhook() throws Exception {

  }

}
