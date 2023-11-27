package com.store.store.product;

import com.store.store.user.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {

        List<Product> products = new ArrayList<Product>();

        productRepository.findAll().forEach(products::add);

        return products;
    }
}
