package com.sunny.webapp_bookstore.web.controllers;

import com.sunny.webapp_bookstore.clients.orders.CreateOrderRequest;
import com.sunny.webapp_bookstore.clients.orders.OrderConfirmationDTO;
import com.sunny.webapp_bookstore.clients.orders.OrderDTO;
import com.sunny.webapp_bookstore.clients.orders.OrderServiceClient;
import com.sunny.webapp_bookstore.clients.orders.OrderSummary;
//import com.sunny.webapp_bookstore.services.SecurityHelper;
import com.sunny.webapp_bookstore.services.SecurityHelper;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderServiceClient orderServiceClient;

    private final SecurityHelper securityHelper;

    OrderController(OrderServiceClient orderServiceClient, SecurityHelper securityHelper) {
        this.orderServiceClient = orderServiceClient;
        this.securityHelper = securityHelper;
    }

    @GetMapping("/cart")
    String cart() {
        return "cart";
    }

    @PostMapping("/api/orders")
    @ResponseBody
    OrderConfirmationDTO createOrder(@Valid @RequestBody CreateOrderRequest orderRequest) {
        String accessToken = securityHelper.getAccessToken();
        Map<String, ?> headers = Map.of("Authorization", "Bearer " + accessToken);
        log.info("Creating order: {}", orderRequest);
        return orderServiceClient.createOrder(headers, orderRequest);
    }

    @GetMapping("/orders/{orderNumber}")
    String showOrderDetails(@PathVariable String orderNumber, Model model) {
        model.addAttribute("orderNumber", orderNumber);
        return "order_details";
    }

    @GetMapping("/api/orders/{orderNumber}")
    @ResponseBody
    OrderDTO getOrder(@PathVariable String orderNumber) {
        String accessToken = securityHelper.getAccessToken();
        Map<String, ?> headers = Map.of("Authorization", "Bearer " + accessToken);
        log.info("Fetching order details for orderNumber: {}", orderNumber);
        return orderServiceClient.getOrder(headers, orderNumber);
    }

    @GetMapping("/orders")
    String showOrders() {
        return "orders";
    }

    @GetMapping("/api/orders")
    @ResponseBody
    List<OrderSummary> getOrders() {
        String accessToken = securityHelper.getAccessToken();
        Map<String, ?> headers = Map.of("Authorization", "Bearer " + accessToken);
        log.info("Fetching orders");
        return orderServiceClient.getOrders(headers);
    }
}
