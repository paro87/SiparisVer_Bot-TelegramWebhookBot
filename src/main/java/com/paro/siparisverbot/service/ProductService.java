package com.paro.siparisverbot.service;

import com.paro.siparisverbot.model.Product;
import com.paro.siparisverbot.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductByID(String productId){
        return productRepository.findById(productId).orElseThrow(()->new NoSuchElementException());
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }
}
