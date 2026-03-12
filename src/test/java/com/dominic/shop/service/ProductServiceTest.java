package com.dominic.shop.service;

import com.dominic.shop.dto.ProductDto;
import com.dominic.shop.entity.Product;
import com.dominic.shop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void should_returnProduct_when_productIsCreated() {
        when(repository.save(any())).thenReturn(new Product(1L, "Laptop", 999.99));

        ProductDto result = service.create(new ProductDto(null, "Laptop", 999.99));

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Laptop");
        assertThat(result.getPrice()).isEqualTo(999.99);
    }

    @Test
    void should_returnAllProducts_when_productsExist() {
        when(repository.findAll()).thenReturn(List.of(
                new Product(1L, "Laptop", 999.99),
                new Product(2L, "Mouse", 29.99)
        ));

        List<ProductDto> result = service.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Laptop");
        assertThat(result.get(1).getName()).isEqualTo("Mouse");
    }

    @Test
    void should_returnEmptyList_when_noProductsExist() {
        when(repository.findAll()).thenReturn(List.of());

        List<ProductDto> result = service.getAll();

        assertThat(result).isEmpty();
    }
}
