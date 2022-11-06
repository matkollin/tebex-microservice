package at.matkollin.service.tebex.service;

import at.matkollin.service.tebex.controller.WebhookController;
import at.matkollin.service.tebex.dto.validation.ValidationDTO;
import at.matkollin.service.tebex.dto.validation.ValidationResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WebhookController.class)
public class ValidationServiceTest {

  @InjectMocks
  private ValidationService validationService;

  @Test
  @DisplayName("Test webhook authentication with address and secret")
  public void asd2() {
    ValidationDTO validationDTO = ValidationDTO.builder()
            .date("2020-01-01")
            .id("123")
            .type("test")
            .build();

    ValidationResponseDTO validationResponseDTO = this.validationService.generateValidationResponseDTO(validationDTO);
    Assertions.assertNotNull(validationResponseDTO);
    Assertions.assertNotNull(validationResponseDTO.getId());
    Assertions.assertEquals(validationResponseDTO.getId(), validationDTO.getId());
  }

}
