package com.store.services;

import com.store.controllers.UserController;
import com.store.enums.Operation;
import com.store.exceptions.InventorySubtractException;
import com.store.entities.Inventory;
import com.store.entities.Product;
import com.store.repositories.ProductRepository;
import com.store.entities.User;
import com.store.repositories.InventoryRepository;
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

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    Logger logger = LoggerFactory.getLogger(UserController.class);


    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public List<Inventory> getInventory(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        List<Inventory> inventories = new ArrayList<Inventory>();

        inventoryRepository.findAll(pageRequest).forEach(inventories::add);

        return inventories;
    }

    public List<Inventory> getInventoryByProduct(Long productId, int page, int size) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            throw new EntityNotFoundException();
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        List<Inventory> inventories = new ArrayList<Inventory>();

        inventoryRepository.findByProductId(productId, pageRequest).forEach(inventories::add);

        return inventories;
    }

    public void addProductQuantityToInventory(Long productId, Integer quantity, User user) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Product product = optionalProduct.get();

        try {
            Inventory inventory = new Inventory();

            inventory.setQuantity(quantity);
            inventory.setOperation(Operation.ADD);
            inventory.setProduct(product);
            inventory.setUser(user);

            inventory.setCreatedAt(LocalDateTime.now());
            inventory.setUpdatedAt(LocalDateTime.now());

            logger.info("Adding " + quantity + "for product " + product.getId());
            inventoryRepository.save(inventory);
            logger.info("Successfully added " + quantity + "for product " + product.getId());
        } catch (Exception exception) {
            logger.info("Could not add " + quantity + "for product " + product.getId(), exception);
            logger.error("Error on addProductQuantityToInventory", exception);

            throw new Exception();
        }
    }

    public void subtractProductQuantityFromInventory(Long productId, Integer quantity, User user) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Product product = optionalProduct.get();

        int stockReceiptsQuantity = inventoryRepository.getStockReceipts(product);
        int stockIssuesQuantity = inventoryRepository.getStockIssues(product);

        if ((stockReceiptsQuantity - stockIssuesQuantity - quantity) < 0) {
            logger.info("Could not subtract " + quantity + "for product " + product.getId());

            throw new InventorySubtractException();
        }

        try {
            Inventory inventory = new Inventory();

            inventory.setQuantity(quantity);
            inventory.setOperation(Operation.SUBTRACT);
            inventory.setProduct(product);
            inventory.setUser(user);

            inventory.setCreatedAt(LocalDateTime.now());
            inventory.setUpdatedAt(LocalDateTime.now());

            logger.info("Subtract " + quantity + "from product " + product.getId());
            inventoryRepository.save(inventory);
            logger.info("Successfully subtracted " + quantity + "for product " + product.getId());
        } catch (Exception exception) {
            logger.info("Could not subtract " + quantity + "for product " + product.getId(), exception);
            logger.error("Error on subtractProductQuantityFromInventory", exception);

            throw new Exception();
        }
    }
}
