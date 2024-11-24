package com.enoca.e_commerce.service;

import com.enoca.e_commerce.dto.ProductRequest;
import com.enoca.e_commerce.dto.ProductResponse;
import com.enoca.e_commerce.dto.ProductUpdateRequest;
import com.enoca.e_commerce.model.Product;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getProductsByName(String name);

    ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest);

    void deleteProduct(Long id);

    Product getProduct(Long id);
}
