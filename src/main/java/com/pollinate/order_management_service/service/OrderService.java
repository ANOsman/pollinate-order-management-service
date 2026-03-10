package com.pollinate.order_management_service.service;

import com.pollinate.order_management_service.dto.OrderRequest;
import com.pollinate.order_management_service.entity.Order;
import com.pollinate.order_management_service.entity.Product;
import com.pollinate.order_management_service.exception.OrderNotFoundException;
import com.pollinate.order_management_service.exception.ProductNotFoundException;
import com.pollinate.order_management_service.repository.OrderRepository;
import com.pollinate.order_management_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Order> findAllOrders() {
        log.info("Fetching all orders");
        return this.orderRepository.findAll();
    }

    public Order findOrder(Long id) {
        log.info("Fetching product with id: {}", id);
        return this.orderRepository.findById(id).
                orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
    }

    public Order createOrder(OrderRequest orderRequest) {
        log.info("Creating order for products with ids {}", orderRequest.getProductIds());

        if (orderRequest.getProductIds() == null || orderRequest.getProductIds().isEmpty()) {
            log.warn("Invalid order request: empty product list");
            throw new IllegalArgumentException("Order must contain at least one product");
        }

        // Remove duplicate IDs
        Set<Long> uniqueProductIds = new HashSet<>(orderRequest.getProductIds());

        // Get products from our database
        List<Product> products = productRepository.findAllById(uniqueProductIds);

        // Validate all products exist
        if (products.size() != uniqueProductIds.size()) {
            throw new ProductNotFoundException("One or more products do not exist");
        }

        // Calculate total price
        BigDecimal totalPrice = products.stream().map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order and save it into the database
        Order order = new Order(totalPrice, products, LocalDateTime.now());

        log.info("Order created {} with total price {}", order.getId(), totalPrice);

        return orderRepository.save(order);
    }
}