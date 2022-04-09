package com.example.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration("swagger")
public class SwaggerProperties {
    private String applicationName;
    private String applicationDescription;
    private String applicationVersion;
}
