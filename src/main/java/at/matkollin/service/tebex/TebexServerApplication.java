package at.matkollin.service.tebex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TebexServerApplication {

  public static Gson GSON = new GsonBuilder().create();
  public static String TEBEX_URL = "https://plugin.tebex.io";

  public static void main(String[] args){
    SpringApplication.run(TebexServerApplication.class, args);
  }

}
