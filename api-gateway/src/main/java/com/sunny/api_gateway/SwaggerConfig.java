package com.sunny.api_gateway;

import static org.springdoc.core.utils.Constants.DEFAULT_API_DOCS_URL;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

/* Dynamic way of setting the urls for the swagger docs where we don't need to add a url or make static url when a new service is
added to the routes, this config will take care of it */
@Configuration
class SwaggerConfig {
    private final RouteDefinitionLocator locator;
    private final SwaggerUiConfigProperties swaggerUiConfigProperties;

    public SwaggerConfig(RouteDefinitionLocator locator, SwaggerUiConfigProperties swaggerUiConfigProperties) {
        this.locator = locator;
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
    }

    @PostConstruct
    public void init() {
        List<RouteDefinition> definitions =
                locator.getRouteDefinitions().collectList().block();
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
        definitions.stream()
                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                .forEach(routeDefinition -> {
                    String name = routeDefinition.getId().replaceAll("-service", "");
                    AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl =
                            new AbstractSwaggerUiConfigProperties.SwaggerUrl(
                                    name, DEFAULT_API_DOCS_URL + "/" + name, null);
                    urls.add(swaggerUrl);
                });
        swaggerUiConfigProperties.setUrls(urls);
    }
}
