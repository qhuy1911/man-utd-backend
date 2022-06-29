package com.example.manutdbackend.controllers;

import com.example.manutdbackend.exception.ResourceNotFoundException;
import com.example.manutdbackend.models.Order;
import com.example.manutdbackend.repository.OrderRepository;
import com.example.manutdbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);
        if (orders.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable("userId") Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Not found USER with id =" + userId);
        }
        List<Order> orders = orderRepository.findByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found ORDER with id =" + id));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable("userId") Long userId,
                                             @RequestBody Order order) {
        Order _order = userRepository.findById(userId).map(user -> {
            order.setOrderDate(new Date());
            order.setStatus(false);
            order.setUser(user);
            return orderRepository.save(order);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found USER with id =" + userId));

        return new ResponseEntity<>(_order, HttpStatus.CREATED);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateStatusOrder(@PathVariable("id") Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found ORDER with id =" + id));
        order.setStatus(!order.isStatus());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
