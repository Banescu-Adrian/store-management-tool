package com.store.store.controllers;

import com.store.store.entities.Inventory;
import com.store.store.dtos.InventoryDTO;
import com.store.store.services.InventoryService;
import com.store.store.exceptions.InventorySubtractException;
import com.store.store.entities.User;
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

@SecurityScheme(
        name = "Authorization",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
@SecurityRequirement(name = "Authorization")
@Tag(name = "Inventory")
@RestController()
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping()
    public List<Inventory> getInventory(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return inventoryService.getInventory(page, size);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity getInventoryByProduct(
            @PathVariable("productId") Long productId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        List<Inventory> inventory;

        try {
            inventory = inventoryService.getInventoryByProduct(productId, page, size);
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(inventory);
    }

    @PostMapping("/products/{productId}/add")
    public ResponseEntity addProductQuantityToInventory(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody InventoryDTO inventoryDTO,
            @AuthenticationPrincipal User user
    ) {

        try {
            inventoryService.addProductQuantityToInventory(productId, inventoryDTO.getQuantity(), user);

        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products/{productId}/subtract")
    public ResponseEntity subtractProductQuantityFromInventory(
            @PathVariable("productId") Long productId,
            @Valid @RequestBody InventoryDTO inventoryDTO,
            @AuthenticationPrincipal User user
    ) {

        try {
            inventoryService.subtractProductQuantityFromInventory(productId, inventoryDTO.getQuantity(), user);

        } catch (EntityNotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (InventorySubtractException exception) {
            return new ResponseEntity("Not enough stock available", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.noContent().build();
    }
}
