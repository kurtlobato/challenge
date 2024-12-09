package com.kurtlobato.challenge;

import com.kurtlobato.challenge.dto.OrderDTO;
import com.kurtlobato.challenge.dto.OrderItemDTO;
import com.kurtlobato.challenge.exception.ValidationException;
import com.kurtlobato.challenge.service.OrderService;
import com.kurtlobato.challenge.validator.OrderValidator;
import com.kurtlobato.challenge.validator.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderValidator orderValidator;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderValidator, Executors.newFixedThreadPool(4));
    }

    @Test
    void testValidOrderOK() {
        OrderDTO order = new OrderDTO(BigInteger.ONE, BigInteger.ONE, BigDecimal.ONE, List.of(BigInteger.ONE, BigInteger.TWO));
        when(orderValidator.validate(order)).thenReturn(List.of());

        List<OrderItemDTO> processedItems = orderService.processOrder(order);

        assertEquals(2, processedItems.size());
        assertNotNull(processedItems.getFirst().getPrice());
        verify(orderValidator).validate(order);
    }

    @Test
    void testInvalidOrder() {
        OrderDTO order = new OrderDTO(BigInteger.ONE, BigInteger.ONE, BigDecimal.ONE, List.of(BigInteger.ONE));
        List<ValidationError> errors = List.of(new ValidationError());
        when(orderValidator.validate(order)).thenReturn(errors);

        ValidationException exception = assertThrows(ValidationException.class, () -> orderService.processOrder(order));
        assertEquals("order is not valid", exception.getMessage());
        assertEquals(errors, exception.getValidationErrors());
        verify(orderValidator).validate(order);
    }

    @Test
    void testCache() {
        BigInteger cachedItem = BigInteger.ONE;
        BigDecimal price = BigDecimal.TEN;
        OrderItemDTO cachedOrderItem = new OrderItemDTO(cachedItem, price);
        orderService.orderItemCache.put(cachedItem, cachedOrderItem);

        OrderDTO order = new OrderDTO(BigInteger.ONE, BigInteger.ONE, BigDecimal.ONE, List.of(cachedItem));
        when(orderValidator.validate(order)).thenReturn(List.of());

        List<OrderItemDTO> processedItems = orderService.processOrder(order);

        assertEquals(1, processedItems.size());
        assertEquals(price, processedItems.getFirst().getPrice());
        verify(orderValidator).validate(order);
    }

    @Test
    void testConcurrentProcessing() {
        OrderDTO order = new OrderDTO(BigInteger.ONE, BigInteger.ONE, BigDecimal.ONE, List.of(BigInteger.ONE, BigInteger.TWO, BigInteger.TEN));
        when(orderValidator.validate(order)).thenReturn(List.of());

        List<OrderItemDTO> processedItems = orderService.processOrder(order);

        assertEquals(3, processedItems.size());
        assertTrue(processedItems.stream().allMatch(item -> item.getPrice().compareTo(BigDecimal.ZERO) > 0));
        verify(orderValidator).validate(order);
    }
}
