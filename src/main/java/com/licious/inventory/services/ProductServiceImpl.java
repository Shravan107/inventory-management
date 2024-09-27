package com.licious.inventory.services;

import com.licious.inventory.dto.ProductResponse;
import com.licious.inventory.entities.Product;
import com.licious.inventory.exceptions.ProductNotFoundException;
import com.licious.inventory.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Product.
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private  ProductRepository productRepository;

    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));
        return mapToDTO(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ProductResponse mapToDTO(Product product) {
        ProductResponse productResponseDTO = new ProductResponse();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setMaxQuantity(product.getMaxQuantity());
        return productResponseDTO;
    }

}
