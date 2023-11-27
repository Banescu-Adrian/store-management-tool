package com.store.services;

import com.store.dtos.CreateProductDTO;
import com.store.entities.Product;
import com.store.entities.User;
import com.store.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;
    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, priceService);
    }

    @Test
    void canGetProducts() {
//        Page<Product> productsMocks = Mockito.mock(Page.class);
//
//        when(productRepository.findAll(Mockito.any(Pageable.class))).thenReturn(productsMocks);
//
//        List<Product> products = productService.getProducts(1, 10);
//
//        Assertions.assertThat(products).isNotNull();
    }

    @Test
    void canCreateProduct() {
//        Long userId = 1L;
//        Long productId = 1L;
//
//        User user = new User();
//        user.setId(userId);
//
//        Product product = new Product();
//        product.setId(productId);
//        product.setName("product");
//        product.setPrice(10.0);
//
//        doNothing().when(productRepository).save(product);
//
//        CreateProductDTO createProductDTO = new CreateProductDTO(
//                product.getName(),
//                product.getPrice()
//        );
//
//        assertAll(() -> productService.createProduct(createProductDTO, user));
    }

    @Test
    void canDeleteProduct() {
//        Long productId = 1L;
//
//        Product product = new Product();
//        product.setId(productId);
//
//        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(product));
//
//        doNothing().when(productRepository).delete(product);
//
//        assertAll(() -> productService.deleteProduct(productId));
    }
}
