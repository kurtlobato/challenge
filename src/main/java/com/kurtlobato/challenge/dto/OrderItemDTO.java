package com.kurtlobato.challenge.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class OrderItemDTO {

    private BigInteger id;
    private BigDecimal price;

    public OrderItemDTO() {
    }

    public OrderItemDTO(BigInteger id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
