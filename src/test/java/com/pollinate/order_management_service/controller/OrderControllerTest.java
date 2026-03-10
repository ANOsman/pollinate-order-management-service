package com.pollinate.order_management_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.pollinate.order_management_service.dto.OrderRequest;
import com.pollinate.order_management_service.entity.Product;
import com.pollinate.order_management_service.repository.OrderRepository;
import com.pollinate.order_management_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // Delete orders first because they have a foreign key dependency on products
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void shouldReturnOrders() throws Exception {
        Product product = productRepository.save(new Product(null, "TV", new BigDecimal("10000")));

        OrderRequest request = new OrderRequest();
        request.setProductIds(List.of(product.getId()));

        // Create an order first
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(httpBasic("admin", "password")))
                .andExpect(status().isOk());

        // Fetch orders
        mockMvc.perform(get("/orders")
                        .with(httpBasic("admin", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].products[0].name").value("TV"));
    }

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {

        Product product = new Product(null, "Desk", new BigDecimal("5000"));
        product = productRepository.save(product);

        OrderRequest request = new OrderRequest();
        request.setProductIds(List.of(product.getId()));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(httpBasic("admin", "password")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnauthorized() throws Exception {

        Product product = new Product(null, "Laptop", new BigDecimal("20000"));
        product = productRepository.save(product);

        OrderRequest request = new OrderRequest();
        request.setProductIds(List.of(product.getId()));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(httpBasic("wrong", "pass")))
                .andExpect(status().isUnauthorized());
    }
}