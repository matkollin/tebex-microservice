package at.matkollin.service.tebex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookController {

  @PostMapping
  public ResponseEntity<HttpStatus> webhook() {
    return ResponseEntity.ok().build();
  }

}
