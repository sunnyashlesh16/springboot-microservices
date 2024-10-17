package com.sunny.order_service.web.controller.demo;

import com.sunny.order_service.ApplicationProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQDEMO {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    public RabbitMQDEMO(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody String message) {
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), "new-orders", message);
    }
}
