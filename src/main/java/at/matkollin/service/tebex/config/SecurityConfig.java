package at.matkollin.service.tebex.config;

import at.matkollin.service.tebex.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@ComponentScan("at.matkollin.service.tebex")
public class SecurityConfig {

  private final ValidationService validationService;

  @Autowired
  public SecurityConfig(ValidationService validationService) {
    this.validationService = validationService;
  }

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.addFilterBefore(new TebexAuthenticationFilter(this.validationService), BasicAuthenticationFilter.class)
            .csrf().disable();
    return http.build();
  }

}
