package com.licious.inventory.repositories;

import com.licious.inventory.entities.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for the InventoryTransaction entity.
 */
@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
    /**
     * Finds all transactions for a product.
     *
     * @param productId The ID of the product to find transactions for.
     * @return The list of transactions for the product.
     */
    List<InventoryTransaction> findByProductId(Long productId);
}
