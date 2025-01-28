package com.sunny.webapp_bookstore.clients.orders;

public record OrderConfirmationDTO(String orderNumber, OrderStatus status) {}
