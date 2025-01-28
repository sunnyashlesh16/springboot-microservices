package com.sunny.webapp_bookstore.clients.catalog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface CatalogServiceClient {

    @GetExchange("/api/products")
    PagedResult<Product> getProducts(@RequestParam int page);

    @GetExchange("/api/products/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code);
}
