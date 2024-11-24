package com.enoca.e_commerce.controller;

import com.enoca.e_commerce.dto.ProductRequest;
import com.enoca.e_commerce.dto.ProductResponse;
import com.enoca.e_commerce.dto.ProductUpdateRequest;
import com.enoca.e_commerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> CreateProduct(@Valid @RequestBody ProductRequest productRequest){
        return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProductsByName(@RequestParam(name = "name") String name){
        return new ResponseEntity<>(productService.getProductsByName(name), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<ProductResponse> updateProduct(@Valid @RequestBody ProductUpdateRequest productUpdateRequest){
        return new ResponseEntity<>(productService.updateProduct(productUpdateRequest), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted success.", HttpStatus.OK);
    }
}
