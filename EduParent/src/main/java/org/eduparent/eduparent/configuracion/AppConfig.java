package org.eduparent.eduparent.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    public AppConfig() {
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
