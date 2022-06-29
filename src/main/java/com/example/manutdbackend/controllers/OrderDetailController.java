package com.example.manutdbackend.controllers;

import com.example.manutdbackend.exception.ResourceNotFoundException;
import com.example.manutdbackend.models.Order;
import com.example.manutdbackend.models.OrderDetail;
import com.example.manutdbackend.models.Size;
import com.example.manutdbackend.repository.OrderDetailRepository;
import com.example.manutdbackend.repository.OrderRepository;
import com.example.manutdbackend.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1")
public class OrderDetailController {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SizeRepository sizeRepository;

    @GetMapping("/details")
    public ResponseEntity<List<OrderDetail>> getAllOrderDetails() {
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetailRepository.findAll().forEach(orderDetails::add);
        if (orderDetails.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}/details")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByOrderId(@PathVariable("orderId") Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Not found ORDER with id = " + orderId);
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable("id") Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found ORDER DETAIL with id = " + id));
        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

    @PostMapping("/orders/{orderId}/sizes/{sizeId}/details")
    public ResponseEntity<OrderDetail> createOrderDetail(@PathVariable("orderId") Long orderId,
                                                         @PathVariable("sizeId") Long sizeId,
                                                         @RequestBody OrderDetail orderDetail) {
//        Get Order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found ORDER with id = " + orderId));

//        Get Size
        Size size = sizeRepository.findById(sizeId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found SIZE with id = " + sizeId));

        orderDetail.setOrder(order);
        orderDetail.setProduct(size.getProduct());
        orderDetail.setSize(size);

        OrderDetail _orderDetail = orderDetailRepository.save(orderDetail);

        return new ResponseEntity<>(_orderDetail, HttpStatus.CREATED);
    }
}
