package com.pollinate.order_management_service.entity;

import com.pollinate.order_management_service.dto.ProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;

    public static Product toProduct(ProductRequest productRequest) {
        return new Product(null, productRequest.getName(), productRequest.getPrice());
    }
}
