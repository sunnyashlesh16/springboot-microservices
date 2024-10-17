package com.sunny.catalog_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
// Just Checking For the Workflow
// @EnableConfigurationProperties(ApplicationProperties.class)
@ConfigurationPropertiesScan
public class CatalogServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}
