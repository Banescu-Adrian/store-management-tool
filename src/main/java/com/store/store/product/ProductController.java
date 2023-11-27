package com.store.store.product;

import com.store.store.product.dtos.CreateProductDTO;
import com.store.store.product.dtos.UpdateProductDTO;
import com.store.store.product.exceptions.ProductAlreadyExistsException;
import com.store.store.user.User;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SecurityScheme(
        name = "Authorization",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
@SecurityRequirement(name = "Authorization")
@Tag(name = "Products")
@RestController()
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity getProductById(@PathVariable("productId") Long productId) {
        Optional<Product> product;

        try {
            product = productService.getProductById(productId);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping()
    public ResponseEntity createProduct(
            @Valid @RequestBody CreateProductDTO createProductDTO,
            @AuthenticationPrincipal User user
    ) {
        try {
            productService.createProduct(createProductDTO, user);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ProductAlreadyExistsException productAlreadyExistsException) {
            return new ResponseEntity("Product already exists", HttpStatus.CONFLICT);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity updateProduct(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody UpdateProductDTO updateProductDTO,
            @AuthenticationPrincipal User user
    ) {
        try {
            productService.updateProduct(productId, updateProductDTO, user);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProduct(
            @PathVariable("productId") Long productId
    ) {
        try {
            productService.deleteProduct(productId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
