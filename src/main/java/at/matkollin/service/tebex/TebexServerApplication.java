package at.matkollin.service.tebex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class })
public class TebexServerApplication {

  public static Gson GSON = new GsonBuilder().create();

  public static void main(String[] args) {
    SpringApplication.run(TebexServerApplication.class, args);
  }

}
