package at.matkollin.service.tebex.controller;

import at.matkollin.service.tebex.dto.webhook.PaymentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/tebex")
@RestController
public class WebhookController {

  @PostMapping("/webhook")
  public ResponseEntity<HttpStatus> webhook(@RequestBody PaymentDTO paymentDTO) {
    log.info("Received webhook: {}", paymentDTO);
    return ResponseEntity.ok().build();
  }

}
