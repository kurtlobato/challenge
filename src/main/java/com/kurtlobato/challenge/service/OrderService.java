package com.kurtlobato.challenge.service;

import com.kurtlobato.challenge.dto.OrderDTO;
import com.kurtlobato.challenge.exception.ValidationException;
import com.kurtlobato.challenge.validator.OrderValidator;
import com.kurtlobato.challenge.validator.ValidationError;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderValidator orderValidator;

    public OrderService(OrderValidator orderValidator) {
        this.orderValidator = orderValidator;
    }

    public void processOrder(OrderDTO order) {
        List<ValidationError> errors = orderValidator.validate(order);
        if (!errors.isEmpty()) throw new ValidationException("order is not valid", errors);
        
    }

}
