package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.Order;
import com.binaklet.binaklet.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController{
    private OrderService orderService;

    public OrderController (OrderService orderService){
        this.orderService=orderService;
    }

    @GetMapping
    public List<Order> getOrders(){
        return orderService.getAll();
    }

    @PostMapping()
    public Order createOrder(@RequestBody Order newOrder, HttpServletRequest request, @RequestBody String body){
        return orderService.create(newOrder);
    }

    @GetMapping({"/{orderId}"})
    public Order getOrder(@PathVariable Long orderId){
        return orderService.getById(orderId);
    }

    @PostMapping({"/{orderId}"})
    public Order updateOrder(@PathVariable Long orderId, @RequestBody Order newOrder){
        return orderService.update(orderId,newOrder);
    }
}
