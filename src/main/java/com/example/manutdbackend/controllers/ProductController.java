package com.example.manutdbackend.controllers;

import com.example.manutdbackend.exception.ResourceNotFoundException;
import com.example.manutdbackend.models.Category;
import com.example.manutdbackend.models.Product;
import com.example.manutdbackend.repository.CategoryRepository;
import com.example.manutdbackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        if (products.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        Collections.reverse(products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/priceAsc")
    public ResponseEntity<List<Product>> getAllProductsByPriceAsc() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        if (products.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return Integer.compare(o1.getPrice(), o2.getPrice());
            }
        });
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/priceDesc")
    public ResponseEntity<List<Product>> getAllProductsByPriceDesc() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        if (products.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return Integer.compare(o1.getPrice(), o2.getPrice());
            }
        });
        Collections.reverse(products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        if (!categoryRepository.existsById(categoryId))
            throw new ResourceNotFoundException("Not found CATEGORY with id = " + categoryId);
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found PRODUCT with id = " + id));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/categories/{categoryId}/products")
    public ResponseEntity<Product> createProduct(@PathVariable("categoryId") Long categoryId,
                                                 @RequestBody Product product) {
        Product _product = categoryRepository.findById(categoryId).map(category -> {
            product.setCategory(category);
            return productRepository.save(product);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found CATEGORY with id = " + categoryId));
        return new ResponseEntity<>(_product, HttpStatus.CREATED);
    }

    @PutMapping("/categories/{categoryId}/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("categoryId") Long categoryId,
                                                 @PathVariable("id") Long id,
                                                 @RequestBody Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found CATEGORY with id = " + categoryId));

        Product _product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found PRODUCT with id = " + id));

//        Update
        _product.setName(product.getName());
        _product.setDescription(product.getDescription());
        _product.setImage(product.getImage());
        _product.setPrice(product.getPrice());
        _product.setCategory(category);

        return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
