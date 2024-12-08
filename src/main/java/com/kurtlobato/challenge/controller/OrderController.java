package com.kurtlobato.challenge.controller;

import com.kurtlobato.challenge.dto.OrderDTO;
import com.kurtlobato.challenge.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/processOrder")
    public void processOrder(@RequestBody OrderDTO order) {
        this.orderService.processOrder(order);
    }

}
