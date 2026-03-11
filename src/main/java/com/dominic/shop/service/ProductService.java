package com.dominic.shop.service;

import com.dominic.shop.dto.ProductDto;
import com.dominic.shop.entity.Product;
import com.dominic.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductDto create(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        Product saved = repository.save(product);
        return toDto(saved);
    }

    public List<ProductDto> getAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    private ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice());
    }

}
