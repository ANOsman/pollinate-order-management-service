package com.pollinate.order_management_service.controller;

import com.pollinate.order_management_service.dto.ProductRequest;
import com.pollinate.order_management_service.entity.Product;
import com.pollinate.order_management_service.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return this.productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(Long id) {
        return this.productService.findProduct(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody ProductRequest productRequest) {
        Product product = Product.toProduct(productRequest);
        return this.productService.createProduct(product);
    }
}