package typeahead;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by abhay on 11/11/14.
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class WebApplicationConfig {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(WebApplicationConfig.class, args);
    }
}
