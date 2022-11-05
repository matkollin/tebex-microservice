package at.matkollin.service.tebex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tebex")
@RestController
public class WebhookController {

  @PostMapping("/webhook")
  public ResponseEntity<HttpStatus> webhook() {
    return ResponseEntity.ok().build();
  }

}
