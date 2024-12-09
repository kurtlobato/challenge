package com.kurtlobato.challenge.controller;

import com.kurtlobato.challenge.dto.OrderDTO;
import com.kurtlobato.challenge.dto.OrderItemDTO;
import com.kurtlobato.challenge.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/processOrder")
    public List<OrderItemDTO> processOrder(@RequestBody OrderDTO order) {
        return this.orderService.processOrder(order);
    }

}
