package at.matkollin.service.tebex.config;

import at.matkollin.service.tebex.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  private final ValidationService validationService;

  @Autowired
  public SecurityConfig(ValidationService validationService) {
    this.validationService = validationService;
  }

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests()
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            .httpBasic().disable()
            .addFilterBefore(new TebexAuthenticationFilter(this.validationService), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}
