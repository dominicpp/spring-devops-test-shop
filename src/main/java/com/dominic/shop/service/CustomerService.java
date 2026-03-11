package com.dominic.shop.service;

import com.dominic.shop.dto.CustomerDto;
import com.dominic.shop.entity.Customer;
import com.dominic.shop.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerDto create(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        return toDto(repository.save(customer));
    }

    public List<CustomerDto> getAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    private CustomerDto toDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail());
    }

}
