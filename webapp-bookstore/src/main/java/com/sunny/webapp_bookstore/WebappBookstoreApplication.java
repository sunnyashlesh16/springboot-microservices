package com.sunny.webapp_bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WebappBookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappBookstoreApplication.class, args);
	}

}
