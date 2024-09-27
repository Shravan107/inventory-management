package com.licious.inventory.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the inventory of a product. This entity represents the stock of a product that can be stored.
 */
@Getter
@Setter
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    @OneToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;

    @Version
    private Long version;
}
