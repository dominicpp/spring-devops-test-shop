package com.dominic.shop.controller;

import com.dominic.shop.dto.OrderDto;
import com.dominic.shop.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderDto create(@RequestBody OrderDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<OrderDto> getAll() {
        return service.getAll();
    }

}
