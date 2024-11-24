package com.enoca.e_commerce.serviceImpl;

import com.enoca.e_commerce.dto.ProductRequest;
import com.enoca.e_commerce.dto.ProductResponse;
import com.enoca.e_commerce.dto.ProductUpdateRequest;
import com.enoca.e_commerce.exception.ResourceNotFoundException;
import com.enoca.e_commerce.model.Product;
import com.enoca.e_commerce.repository.ProductRepository;
import com.enoca.e_commerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .build();
        Product saveProduct = productRepository.save(product);

        return ProductResponse.builder()
                .name(saveProduct.getName())
                .description(saveProduct.getDescription())
                .price(saveProduct.getPrice())
                .stock(saveProduct.getStock())
                .build();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "+id));

        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    @Override
    public List<ProductResponse> getProductsByName(String name) {

        List<Product> products = productRepository.findByName(name);
        return products.stream().map(this::productMapToProductResponse).collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productUpdateRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "+productUpdateRequest.getId()));

        product.setPrice(productUpdateRequest.getPrice());
        product.setStock(productUpdateRequest.getStock());

        Product updatedProduct = productRepository.save(product);

        return productMapToProductResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "+id));
        productRepository.delete(product);
    }
    public Product getProduct(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "+id));
    }

    private ProductResponse productMapToProductResponse(Product product) {

        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}