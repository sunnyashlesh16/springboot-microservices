package com.sunny.catalog_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
// Just Checking For the Workflow
// @EnableConfigurationProperties(ApplicationProperties.class)
@EnableCaching
@ConfigurationPropertiesScan
public class CatalogServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}
