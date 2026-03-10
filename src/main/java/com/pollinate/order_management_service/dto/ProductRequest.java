package com.pollinate.order_management_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private Long id;
    private String name;
    private BigDecimal price;
}
