package com.store.store.product;

import com.store.store.product.dtos.CreateProductDTO;
import com.store.store.product.dtos.UpdateProductDTO;
import com.store.store.product.exceptions.ProductAlreadyExistsException;
import com.store.store.user.User;
import com.store.store.user.UserController;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class ProductService {
    /**
     * No latin pattern.
     */
    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    /**
     * Whitespace pattern.
     */
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private final ProductRepository productRepository;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        List<Product> products = new ArrayList<Product>();

        productRepository.findAll(pageRequest).forEach(products::add);

        return products;
    }

    public Optional<Product> getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new EntityNotFoundException();
        }

        return product;
    }

    public void createProduct(CreateProductDTO createProductDTO, User user) throws Exception {

        String slug = toSlug(createProductDTO.getName());

        if (productRepository.findBySlugIgnoreCase(slug).isPresent()) {
            throw new ProductAlreadyExistsException();
        }

        try {
            Product product = new Product();

            product.setName(createProductDTO.getName());
            product.setSlug(slug);
            product.setPrice(createProductDTO.getPrice());

            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());
            product.setUser(user);

            productRepository.save(product);
        } catch (Exception exception) {
            logger.error("Error on createProduct", exception);

            throw new Exception();
        }
    }

    public void updateProduct(Long productId, UpdateProductDTO updateProductDTO, User user) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException();
        }

        try {
            Product product = optionalProduct.get();

            product.setPrice(updateProductDTO.getPrice());

            product.setUpdatedAt(LocalDateTime.now());
            product.setUser(user);

            productRepository.save(product);
        } catch (Exception exception) {
            logger.error("Error on updateProduct", exception);

            throw new Exception();
        }
    }

    public void deleteProduct(Long productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException();
        }

        try {
            Product product = optionalProduct.get();

            productRepository.delete(product);
        } catch (Exception exception) {
            logger.error("Error on deleteProduct", exception);

            throw new Exception();
        }
    }

    private static String toSlug(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
