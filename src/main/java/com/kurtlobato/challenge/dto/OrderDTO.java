package com.kurtlobato.challenge.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class OrderDTO {

    private BigInteger orderId;
    private BigInteger customerId;
    private BigDecimal orderAmount;
    private List<BigInteger> orderItems;

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public BigInteger getCustomerId() {
        return customerId;
    }

    public void setCustomerId(BigInteger customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public List<BigInteger> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<BigInteger> orderItems) {
        this.orderItems = orderItems;
    }
}
