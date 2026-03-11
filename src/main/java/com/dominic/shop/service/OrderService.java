package com.dominic.shop.service;

import com.dominic.shop.dto.OrderDto;
import com.dominic.shop.dto.OrderItemDto;
import com.dominic.shop.entity.Customer;
import com.dominic.shop.entity.Order;
import com.dominic.shop.entity.OrderItem;
import com.dominic.shop.entity.Product;
import com.dominic.shop.exception.ResourceNotFoundException;
import com.dominic.shop.repository.CustomerRepository;
import com.dominic.shop.repository.OrderRepository;
import com.dominic.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public OrderDto create(OrderDto dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + dto.getCustomerId()));

        Order order = new Order();
        order.setCustomer(customer);

        for (OrderItemDto itemDto : dto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemDto.getProductId()));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            order.getItems().add(item);
        }

        return toDto(orderRepository.save(order));
    }

    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    private OrderDto toDto(Order order) {
        List<OrderItemDto> items = order.getItems().stream()
                .map(i -> new OrderItemDto(i.getProduct().getId(), i.getQuantity()))
                .toList();
        return new OrderDto(order.getId(), order.getCustomer().getId(), items);
    }

}
