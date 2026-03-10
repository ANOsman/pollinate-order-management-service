package com.pollinate.order_management_service.service;

import com.pollinate.order_management_service.dto.OrderRequest;
import com.pollinate.order_management_service.entity.Product;
import com.pollinate.order_management_service.repository.OrderRepository;
import com.pollinate.order_management_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrderSuccessfully() {

        Product p1 = new Product(1L, "Keyboard", new BigDecimal("1000"));
        Product p2 = new Product(2L, "Mouse", new BigDecimal("300"));

        OrderRequest request = new OrderRequest();
        request.setProductIds(List.of(1L, 2L));

        when(productRepository.findAllById(any())).thenReturn(List.of(p1, p2));

        orderService.createOrder(request);

        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void shouldFailWhenProductDoesNotExist() {

        OrderRequest request = new OrderRequest();
        request.setProductIds(List.of(1L, 2L));

        when(productRepository.findAllById(any())).thenReturn(List.of());

        assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(request);
        });
    }
}