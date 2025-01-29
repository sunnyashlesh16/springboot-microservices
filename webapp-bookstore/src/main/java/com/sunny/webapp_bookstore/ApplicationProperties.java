package com.sunny.webapp_bookstore;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bookstore")
public record ApplicationProperties(String apiCatalogUrl, String apiOrdersUrl) {}
