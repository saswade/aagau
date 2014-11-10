package typeahead.trie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by abhay on 11/8/14.
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApplicationConfig {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(ApplicationConfig.class, args);
    }
}
