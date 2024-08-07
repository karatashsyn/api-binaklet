package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.dto.requests.order.CreateOrderRequest;
import com.binaklet.binaklet.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController{
    private final OrderService orderService;

    @GetMapping("/me")
    public ResponseEntity<List<Order>> getMyOrders(){
        return orderService.getMyOrders();
    }

    @PostMapping()
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderRequest request){
        return orderService.create(request);

    }

}
