package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.enums.OrderStatus;
import com.binaklet.binaklet.requests.OrderCreateRequest;
import com.binaklet.binaklet.services.*;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController{
    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;
    private final TransporterService transporterService;
    private final AddressService addressService;


//    @GetMapping
//    public List<Order> getOrders(){
//        return orderService.getAll();
//    }

    @PostMapping()
    public ResponseEntity <Order> createOrder(@RequestBody OrderCreateRequest request){
        System.out.println(request.toString());
        if(request.getItemIds().length<1 || request.getSellerId()==null || request.getDeliverAddressId()==null || request.getPickUpAddressId()==null){
            return ResponseEntity.badRequest().body(null);
        }
        Order createdOrder =orderService.create(request);
        if(createdOrder!=null){
            return ResponseEntity.ok(createdOrder);
        }
        else{
            return  ResponseEntity.badRequest().body(null);
        }

    }

//    @GetMapping({"/{orderId}"})
//    public Order getOrder(@PathVariable Long orderId){
//        return orderService.getById(orderId);
//    }

//    @PostMapping({"/{orderId}"})
//    public Order updateOrder(@PathVariable Long orderId, @RequestBody Order newOrder){
//        return orderService.update(orderId,newOrder);
//    }
}
