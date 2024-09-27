package com.licious.inventory.repositories;

import com.licious.inventory.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the Product entity.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
