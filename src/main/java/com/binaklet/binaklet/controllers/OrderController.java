package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.*;
import dto.requests.order.OrderCreateRequest;
import com.binaklet.binaklet.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if(request.getItemIds().length<1 || request.getSellerId()==null || request.getDeliverAddress()==null || request.getPickupAddress()==null){
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
