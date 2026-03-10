package com.pollinate.order_management_service.controller;

import com.pollinate.order_management_service.dto.OrderRequest;
import com.pollinate.order_management_service.entity.Order;
import com.pollinate.order_management_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return this.orderService.findAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(Long id) {
        return orderService.findOrder(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest orderRequest) {
        return this.orderService.createOrder(orderRequest);
    }
}