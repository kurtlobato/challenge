package com.kurtlobato.challenge;

import com.kurtlobato.challenge.dto.OrderDTO;
import com.kurtlobato.challenge.validator.OrderValidator;
import com.kurtlobato.challenge.validator.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static com.kurtlobato.challenge.validator.ValidationErrorType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderValidatorTest {

    private OrderValidator orderValidator;

    @BeforeEach
    public void setUp() {
        orderValidator = new OrderValidator();
    }

    @Test
    public void testOrderIdLessThanOrEqualToZero() {
        OrderDTO order = new OrderDTO(BigInteger.ZERO, BigInteger.TEN, BigDecimal.TEN, List.of(BigInteger.ONE));
        List<ValidationError> errors = orderValidator.validate(order);
        assertEquals(1, errors.size());
        assertEquals("orderId", errors.getFirst().getField());
        assertEquals(VALUE_MUST_BE_GREATER_THAN_ZERO, errors.getFirst().getType());
    }

    @Test
    public void testCustomerIdLessThanOrEqualToZero() {
        OrderDTO order = new OrderDTO(BigInteger.TEN, BigInteger.ZERO, BigDecimal.TEN, List.of(BigInteger.ONE));
        List<ValidationError> errors = orderValidator.validate(order);
        assertEquals(1, errors.size());
        assertEquals("customerId", errors.getFirst().getField());
        assertEquals(VALUE_MUST_BE_GREATER_THAN_ZERO, errors.getFirst().getType());
    }

    @Test
    public void testOrderAmountNegative() {
        OrderDTO order = new OrderDTO(BigInteger.TEN, BigInteger.TEN, BigDecimal.valueOf(-1), List.of(BigInteger.ONE));
        List<ValidationError> errors = orderValidator.validate(order);
        assertEquals(1, errors.size());
        assertEquals("orderAmount", errors.getFirst().getField());
        assertEquals(VALUE_MUST_NOT_BE_NEGATIVE, errors.getFirst().getType());
    }

    @Test
    public void testEmptyOrderItems() {
        OrderDTO order = new OrderDTO(BigInteger.TEN, BigInteger.TEN, BigDecimal.TEN, List.of());
        List<ValidationError> errors = orderValidator.validate(order);
        assertEquals(1, errors.size());
        assertEquals("orderItems", errors.getFirst().getField());
        assertEquals(LIST_MUST_NOT_BE_EMPTY, errors.getFirst().getType());
    }

    @Test
    public void testOrderItemNegative() {
        OrderDTO order = new OrderDTO(BigInteger.TEN, BigInteger.TEN, BigDecimal.TEN, List.of(BigInteger.ONE, BigInteger.valueOf(-1)));
        List<ValidationError> errors = orderValidator.validate(order);
        assertEquals(1, errors.size());
        assertEquals("orderItems[1]", errors.getFirst().getField());
        assertEquals(VALUE_MUST_BE_GREATER_THAN_ZERO, errors.getFirst().getType());
    }

    @Test
    public void testOK() {
        OrderDTO order = new OrderDTO(BigInteger.TEN, BigInteger.TEN, BigDecimal.TEN, List.of(BigInteger.ONE, BigInteger.valueOf(2)));
        List<ValidationError> errors = orderValidator.validate(order);
        assertEquals(0, errors.size());
    }
}