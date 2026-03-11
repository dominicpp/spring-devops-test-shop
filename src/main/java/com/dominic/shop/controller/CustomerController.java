package com.dominic.shop.controller;

import com.dominic.shop.dto.CustomerDto;
import com.dominic.shop.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public CustomerDto create(@RequestBody CustomerDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<CustomerDto> getAll() {
        return service.getAll();
    }

}
