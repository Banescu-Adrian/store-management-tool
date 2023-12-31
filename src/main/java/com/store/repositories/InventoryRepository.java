package com.store.repositories;

import com.store.entities.Product;
import com.store.entities.Inventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long>, PagingAndSortingRepository<Inventory, Long> {
    Page<Inventory> findByProductId(Long id, Pageable pageable);

    @Query("select IFNULL(SUM(e.quantity), 0) as quantity from Inventory e where e.product = ?1 and e.operation = 1")
    int getStockReceipts(
            Product product
    );

    @Query("select IFNULL(SUM(e.quantity), 0) as quantity from Inventory e where e.product = ?1 and e.operation = 0")
    int getStockIssues(
            Product product
    );
}
