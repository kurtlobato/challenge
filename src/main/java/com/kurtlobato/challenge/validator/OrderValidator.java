package com.kurtlobato.challenge.validator;

import com.kurtlobato.challenge.dto.OrderDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.kurtlobato.challenge.validator.ValidationErrorType.*;

@Component
public class OrderValidator implements Validator<OrderDTO> {

    @Override
    public List<ValidationError> validate(OrderDTO order) {
        List<ValidationError> errors = new ArrayList<>();
        if (order.getOrderId().compareTo(BigInteger.ZERO) <= 0) errors.add(buildError("orderId", VALUE_MUST_BE_GREATER_THAN_ZERO, "orderId must be greater than zero"));
        if (order.getCustomerId().compareTo(BigInteger.ZERO) <= 0) errors.add(buildError("customerId", VALUE_MUST_BE_GREATER_THAN_ZERO, "customerId must be greater than zero"));
        if (order.getOrderAmount().compareTo(BigDecimal.ZERO) < 0) errors.add(buildError("orderAmount", VALUE_MUST_NOT_BE_NEGATIVE, "orderAmount can't be negative"));
        if (order.getOrderItems().isEmpty()) errors.add(buildError("orderItems", LIST_MUST_NOT_BE_EMPTY, "orderItems can't be empty"));
        for (int i = 0; i < order.getOrderItems().size(); i++) {
            BigInteger item = order.getOrderItems().get(i);
            if (item.compareTo(BigInteger.ZERO) <= 0) errors.add(buildError(String.format("orderItems[%d]", i), VALUE_MUST_BE_GREATER_THAN_ZERO, String.format("orderItems[%d] must be greater than zero", i)));
        }
        return errors;
    }
}
