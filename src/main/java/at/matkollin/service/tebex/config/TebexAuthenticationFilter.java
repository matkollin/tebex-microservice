package at.matkollin.service.tebex.config;

import at.matkollin.service.tebex.TebexServerApplication;
import at.matkollin.service.tebex.dto.validation.ValidationDTO;
import at.matkollin.service.tebex.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

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
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    // Check if the request host address is an authorized address
    if (this.validationService.isIpAddressAuthorized(request.getRemoteAddr())) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    String signature = request.getHeader("X-Signature");
    // Check if the received request signature is valid
    if (!this.validationService.isSignatureValid(signature, body)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    Optional<ValidationDTO> validationDTO = this.validationService.getValidationDTO(body);
    // Check if the received request is a validation request
    if (validationDTO.isPresent()) {
      // Send validation response
      response.setStatus(HttpServletResponse.SC_OK);
      response.setContentType("application/json");
      response.getWriter().write(TebexServerApplication.GSON.toJson(this.validationService.generateValidationResponseDTO(validationDTO.get())));
      return;
    }

    filterChain.doFilter(request, response);
  }

}
