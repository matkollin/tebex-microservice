package at.matkollin.service.tebex.config;

import at.matkollin.service.tebex.TebexServerApplication;
import at.matkollin.service.tebex.dto.validation.ValidationDTO;
import at.matkollin.service.tebex.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class TebexAuthenticationFilter extends OncePerRequestFilter {

  private final ValidationService validationService;

  @Autowired
  public TebexAuthenticationFilter(ValidationService validationService) {
    this.validationService = validationService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // Check if the requested path is a webhook path
    if (!request.getRequestURI().equals("/v1/tebex/webhook")) {
      log.debug("Received request for path: {}", request.getRequestURI());
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    // Check if the request host address is an authorized address
    if (this.validationService.isIpAddressAuthorized(request.getRemoteAddr())) {
      log.debug("Received request from unauthorized address: {}", request.getRemoteAddr());
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    String signature = request.getHeader("X-Signature");
    // Check if the received request signature is valid
    if (signature == null || signature.isBlank() || !this.validationService.isSignatureValid(signature, body)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      log.debug("Received request with invalid signature: {}", signature);
      return;
    }

    Optional<ValidationDTO> validationDTO = this.validationService.getValidationDTO(body);
    // Check if the received request is a validation request
    if (validationDTO.isPresent()) {
      log.debug("Received validation request");
      // Send validation response
      response.setStatus(HttpServletResponse.SC_OK);
      response.setContentType("application/json");
      response.getWriter().write(TebexServerApplication.GSON.toJson(this.validationService.generateValidationResponseDTO(validationDTO.get())));
      return;
    }

    log.debug("Received webhook request");
    filterChain.doFilter(request, response);
  }

}
