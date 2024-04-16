package bruno.spring.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpeonApi(){
        return new OpenAPI().info(new Info()
        .title("RestFull Api with Java 18 and Spring Boot 3").version("v1")
        .description("Api utilizada no curso")
        .termsOfService("http://github.com/Brunouch")
        .license(new License().name("Apache 2.0").url("http://github.com/Brunouch")));
    }
}
