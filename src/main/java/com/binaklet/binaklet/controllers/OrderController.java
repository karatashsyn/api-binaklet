package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.enums.OrderStatus;
import com.binaklet.binaklet.requests.OrderCreateRequest;
import com.binaklet.binaklet.services.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController{
    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;
    private final TransporterService transporterService;
    private final AddressService addressService;
    public OrderController (OrderService orderService, UserService userService, ItemService itemService, TransporterService transporterService, AddressService addressService){
        this.orderService=orderService;
        this.itemService=itemService;
        this.transporterService=transporterService;
        this.userService=userService;
        this.addressService = addressService;
    }

    @GetMapping
    public List<Order> getOrders(){
        return orderService.getAll();
    }

    @PostMapping()
    public Order createOrder(@RequestBody OrderCreateRequest request){
        System.out.println(request.toString());
        Address pickUpAddress = addressService.getById(request.getPickUpAddressId());
        Address deliverAddress = addressService.getById(request.getDeliverAddressId());
        Transporter transporter = transporterService.getById(request.getTransporterId());
        Long[] itemsId = request.getItemsId();
        List<Item> orderItems = new ArrayList<>();
        for (Long id :itemsId
             ) {
            Item foundItem = itemService.getById(id);
            if(foundItem!=null){
                orderItems.add(foundItem);
                foundItem.setStatus(ItemStatus.ITEM_STATUS_SOLD);
            }
        }

        User buyer = userService.getById(request.getBuyerId());
        User seller = userService.getById(request.getSellerId());


        if(buyer!=null && seller!=null && !orderItems.isEmpty()&&pickUpAddress!=null && deliverAddress!=null){
            Order orderToCreate = new Order();
            orderToCreate.setBuyer(buyer);
            orderToCreate.setSeller(seller);
            orderToCreate.setItems(orderItems);
            orderToCreate.setDeliverAddress(deliverAddress);
            orderToCreate.setPickUpAddress(pickUpAddress);
            orderToCreate.setStatus(OrderStatus.ORDER_STATUS_CREATED);
            Transporter transporterOfOrder = transporterService.getById(request.getTransporterId());
            if(transporterOfOrder!=null){
                orderToCreate.setTransporter(transporterOfOrder);
                System.out.println("COUNT: " +transporterService.getActiveOrderCount(transporterOfOrder).toString());
            }
            return orderService.create(orderToCreate);
        }
        else{
            return null;
        }


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
