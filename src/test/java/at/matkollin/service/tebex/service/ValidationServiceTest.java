package at.matkollin.service.tebex.service;

import at.matkollin.service.tebex.controller.WebhookController;
import at.matkollin.service.tebex.dto.validation.ValidationDTO;
import at.matkollin.service.tebex.dto.validation.ValidationResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WebhookController.class)
public class ValidationServiceTest {

  @InjectMocks
  private ValidationService validationService;

  @Test
  @DisplayName("Test wrong authentication address")
  public void testAuthenticatedAddresses() {
    // Set ip addresses since environment variables are not set in tests
    this.validationService.setAuthorizedIpAddresses(new String[]{"18.209.80.3"});

    String address = "127.0.0.1";
    Assertions.assertFalse(this.validationService.isIpAddressAuthorized(address));

    address = "18.209.80.3";
    Assertions.assertTrue(this.validationService.isIpAddressAuthorized(address));
  }

  @Test
  @DisplayName("Test validation response")
  public void testJsonDeserialization() {
    Optional<ValidationDTO> validationDTO = this.validationService.getValidationDTO("{id: 1, type: \"validation.webhook\", date: 123456789}");
    Assertions.assertTrue(validationDTO.isPresent());

    validationDTO = this.validationService.getValidationDTO("{ids: 1, types: \"validation.webhook\", date: 123456789}");
    Assertions.assertFalse(validationDTO.isPresent());
  }

  @Test
  @DisplayName("Test the validation of a webhook")
  public void testValidationDtoGeneration() {
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

  @Test
  @DisplayName("Test the signature of a webhook")
  public void testSignature() {
    this.validationService.setTebexWebhookSecret("secret");

    String body = "test";
    String signature = this.validationService.encodeMessage("secret-wrong", body);
    Assertions.assertNotNull(signature);
    Assertions.assertFalse(this.validationService.isSignatureValid(signature, body));

    signature = this.validationService.encodeMessage("secret", body);
    Assertions.assertNotNull(signature);
    Assertions.assertTrue(this.validationService.isSignatureValid(signature, body));
  }

  @Test
  public void testInvalidSecret() {
    Assertions.assertEquals(this.validationService.encodeMessage(null, "test"), "");
  }

}
