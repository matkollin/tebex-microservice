package at.matkollin.service.tebex.service;

import at.matkollin.service.tebex.TebexServerApplication;
import at.matkollin.service.tebex.dto.validation.ValidationDTO;
import at.matkollin.service.tebex.dto.validation.ValidationResponseDTO;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ValidationService {

  @Value("${TEBEX_WEBHOOK_SECRET:secret}")
  private String tebexWebhookSecret;
  // Authorized ip addresses provided by tebex
  @Value("${TEBEX_WEBHOOK_AUTHORIZED_IP_ADDRESSES:18.209.80.3,54.87.231.232}")
  private String[] authorizedIpAddresses;

  /**
   * Check if the given address is in the authorized address list.
   *
   * @param address address to check.
   * @return whenever the address is authorized.
   */
  public boolean isIpAddressAuthorized(String address) {
    if (this.authorizedIpAddresses.length == 0) {
      // If no addresses are provided, we assume that all addresses are authorized.
      return true;
    }

    return Arrays.asList(this.authorizedIpAddresses).contains(address);
  }

  /**
   * Get a new {@link ValidationResponseDTO} instance parsed from the provided body whenever the provided body is a validation request and
   * the {@link ValidationDTO#getType()} equals to "validation.webhook".
   *
   * @param body the body of a request.
   * @return a {@link ValidationDTO} if the body is a validation request and the type is "validation.webhook". Otherwise an empty {@link Optional}.
   */
  public Optional<ValidationDTO> getValidationDTO(String body) {
    if (!body.contains("id") && !body.contains("type") && !body.contains("date")) {
      return Optional.empty();
    }

    ValidationDTO validationDTO = TebexServerApplication.GSON.fromJson(body, ValidationDTO.class);
    if (validationDTO == null) {
      return Optional.empty();
    }

    if (validationDTO.getType() == null) {
      return Optional.empty();
    }

    return validationDTO.getType().equals("validation.webhook") ? Optional.of(validationDTO) : Optional.empty();
  }

  /**
   * Generate a {@link ValidationResponseDTO} instance based on the provided {@link ValidationDTO}.
   * The {@link ValidationResponseDTO} will contain the id of the provided {@link ValidationDTO}.
   *
   * @param validationDTO the provided {@link ValidationDTO}.
   * @return a new {@link ValidationResponseDTO} instance.
   */
  public ValidationResponseDTO generateValidationResponseDTO(ValidationDTO validationDTO) {
    return ValidationResponseDTO
            .builder()
            .id(validationDTO.getId())
            .build();
  }

  /**
   * Check if the given signature is valid.
   *
   * @param signature the signature to check.
   * @param body the body of the request.
   * @return true if the signature is valid.
   */
  public boolean isSignatureValid(String signature, String body) {
    if (this.tebexWebhookSecret == null || this.tebexWebhookSecret.isEmpty()) {
      // If no secret is provided, we assume that none signatures are valid.
      return false;
    }

    String sha256 = DigestUtils.sha256Hex(body);
    try {
      String sha256Hmac = this.encode(this.tebexWebhookSecret, sha256);
      return signature.equals(sha256Hmac);
    } catch (Exception exception) {
      return false;
    }
  }

  /**
   * Encode the given message with the given secret.
   *
   * @param secret the secret to use.
   * @param message the message to encode.
   * @return the encoded message.
   */
  private String encode(String secret, String message) throws Exception {
    Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
    SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    sha256_HMAC.init(secret_key);

    return Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));
  }

}
