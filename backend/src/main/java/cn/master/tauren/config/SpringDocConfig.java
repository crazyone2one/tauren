package cn.master.tauren.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by 11's papa on 12/06/2024
 **/
@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Tauren--Api")
                        .version("1.0.0"));
    }
}
