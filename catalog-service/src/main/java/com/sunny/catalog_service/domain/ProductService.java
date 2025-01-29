package com.sunny.catalog_service.domain;

import com.sunny.catalog_service.ApplicationProperties;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Main Code For Product Service

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ApplicationProperties properties;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static final String CACHE_NAME = "products";


    ProductService(ProductRepository productRepository, ApplicationProperties properties) {
        this.productRepository = productRepository;
        this.properties = properties;
    }

    public PagedResult<Product> getProducts(int pageNo) {
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(pageNo, properties.pageSize(), sort);
        Page<Product> productsPage = productRepository.findAll(pageable).map(ProductMapper::toProduct);

        return new PagedResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1,
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious());
    }

    public PagedResult<Product> getProductsFromCache(int pageNo) {
        String key = "page-" + pageNo;
        PagedResult<Product> cachedResult = (PagedResult<Product>) redisTemplate.opsForValue().get(key);

        if (cachedResult != null) {
            return cachedResult;
        }

        // If not found in cache, fetch from DB and cache it
        PagedResult<Product> result = getProducts(pageNo);
        redisTemplate.opsForValue().set(key, result);

        return result;
    }

    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code).map(ProductMapper::toProduct);
    }
}
