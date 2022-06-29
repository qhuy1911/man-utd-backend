package com.example.manutdbackend.controllers;

import com.example.manutdbackend.exception.ResourceNotFoundException;
import com.example.manutdbackend.models.Product;
import com.example.manutdbackend.models.Size;
import com.example.manutdbackend.repository.ProductRepository;
import com.example.manutdbackend.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1")
public class SizeController {
    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/sizes")
    public ResponseEntity<List<Size>> getAllSizes() {
        List<Size> sizes = new ArrayList<>();
        sizeRepository.findAll().forEach(sizes::add);
        if (sizes.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}/sizes")
    public ResponseEntity<List<Size>> getSizesByProductId(@PathVariable("productId") Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Not found PRODUCT with id = " + productId);
        }
        List<Size> sizes = sizeRepository.findByProductId(productId);
        return new ResponseEntity<>(sizes, HttpStatus.OK);
    }

    @GetMapping("/sizes/{id}")
    public ResponseEntity<Size> getSizeById(@PathVariable("id") Long id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found SIZE with id = " + id));
        return new ResponseEntity<>(size, HttpStatus.OK);
    }

    @PostMapping("/products/{productId}/sizes")
    public ResponseEntity<Size> createSize(@PathVariable("productId") Long productId,
                                           @RequestBody Size size) {
        Size _size = productRepository.findById(productId).map(product -> {
            size.setProduct(product);
            return sizeRepository.save(size);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found PRODUCT with id = " + productId));

        return new ResponseEntity<>(_size, HttpStatus.CREATED);
    }

    @PutMapping("/sizes/{id}") // only update quantity
    public ResponseEntity<Size> updateSize(@PathVariable("id") Long id,
                                           @RequestBody Size size) {
        Size _size = sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found SIZE with id = " + id));
        _size.setStock(size.getStock());
        return new ResponseEntity<>(sizeRepository.save(_size), HttpStatus.OK);
    }

    @DeleteMapping("/sizes/{id}")
    public ResponseEntity<Size> deleteSize(@PathVariable("id") Long id) {
        sizeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
