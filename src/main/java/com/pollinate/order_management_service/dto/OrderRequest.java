package com.pollinate.order_management_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<Long> productIds;
}