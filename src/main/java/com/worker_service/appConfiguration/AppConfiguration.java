package com.worker_service.appConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Worker Service API")
                        .version("1.0.0")
                        .description("This is the Worker microservice for One-Day-Worker backend")
                        .contact(new Contact()
                                .name("Saurabh Chauhan")
                                .email("csaurabh002@gmail.com")));
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Worker Service API")
                        .description("OpenAPI documentation for Worker microservice")
                        .version("1.0.0"));
    }
}
