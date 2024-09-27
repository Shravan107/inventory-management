package com.licious.inventory.services;

import com.licious.inventory.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    /**
     * Method to get product by id.
     * @param productId Long object.
     * @return ProductResponseDTO object.
     **/
    ProductResponse getProductById(Long productId);

    /**
     * Method to get all the products.
     * @return List of ProductResponseDTO objects.
     */
    List<ProductResponse> getAllProducts();

}
