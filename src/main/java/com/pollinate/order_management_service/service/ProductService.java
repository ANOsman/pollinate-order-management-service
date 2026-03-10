package com.pollinate.order_management_service.service;

import com.pollinate.order_management_service.entity.Product;
import com.pollinate.order_management_service.exception.ProductNotFoundException;
import com.pollinate.order_management_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {

        log.info("Creating product {}", product);
        return this.productRepository.save(product);
    }

    public Product findProduct(Long id) {
        log.info("Fetching product with id {}", id);
        return this.productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with id: " + id));
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        log.info("Getting all products");
        return this.productRepository.findAll(pageable);
    }
}