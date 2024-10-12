package com.sunny.catalog_service;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

// Setting the application properties here for the default page.
@ConfigurationProperties(prefix = "catalog")
public record ApplicationProperties(@DefaultValue("10") @Min(1) int pageSize) {}
