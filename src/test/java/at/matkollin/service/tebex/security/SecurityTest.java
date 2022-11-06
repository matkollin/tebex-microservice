package at.matkollin.service.tebex.security;

import at.matkollin.service.tebex.controller.WebhookController;
import at.matkollin.service.tebex.dto.validation.ValidationDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WebhookController.class)
public class SecurityTest {

  @Autowired
  private MockMvc mockMvc;
  private final ObjectMapper objectMapper= new ObjectMapper();

  @Test
  @DisplayName("Test webhook authentication without anything")
  public void testAuthentication() throws Exception {
    ValidationDTO validationDTO = ValidationDTO.builder()
            .date("2020-01-01")
            .id("123")
            .type("test")
            .build();

    this.mockMvc.perform(post("/v1/tebex/webhook")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(validationDTO))
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("Test webhook authentication with address only")
  public void testAuthenticationWithAddress() throws Exception {
    ValidationDTO validationDTO = ValidationDTO.builder()
            .date("2020-01-01")
            .id("123")
            .type("test")
            .build();

    this.mockMvc.perform(post("/v1/tebex/webhook")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(validationDTO))
                    .with(request->{request.setRemoteAddr("18.209.80.3"); return request;})
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isForbidden());
  }

}
