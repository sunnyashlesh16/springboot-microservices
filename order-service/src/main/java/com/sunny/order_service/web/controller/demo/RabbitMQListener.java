package com.sunny.order_service.web.controller.demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {
    @RabbitListener(queues = "${orders.new-orders-queue}")
    public void handleNewOrder(String message) {
        System.out.println("Print Something" + message);
    }
}
