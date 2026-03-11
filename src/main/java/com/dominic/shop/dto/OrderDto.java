package com.dominic.shop.dto;

import java.util.List;

public class OrderDto {

    private Long id;
    private Long customerId;
    private List<OrderItemDto> items;

    public OrderDto() {}

    public OrderDto(Long id, Long customerId, List<OrderItemDto> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public List<OrderItemDto> getItems() { return items; }
    public void setItems(List<OrderItemDto> items) { this.items = items; }

}
