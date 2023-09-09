package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.enums.OrderStatus;
import com.binaklet.binaklet.repositories.OrderRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.requests.OrderCreateRequest;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService{

    private final AddressService addressService;
    private final UserService userService;
    private final TransporterService transporterService;
    private final ItemService itemService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Order create (OrderCreateRequest request){
        System.out.println(request.toString());
        Address pickUpAddress = addressService.getById(request.getPickUpAddressId());
        Address deliverAddress = addressService.getById(request.getDeliverAddressId());

        Long[] itemsId = request.getItemIds();
        List<Item> orderItems = new ArrayList<>();
        for (Long id :itemsId
        ) {
            Item foundItem = itemService.getById(id);
            if(foundItem!=null){
                orderItems.add(foundItem);
                foundItem.setStatus(ItemStatus.SOLD);
            }
        }


        User seller = userService.getById(request.getSellerId());
//
//
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(currentUser.isPresent()&& seller!=null && !orderItems.isEmpty()&&pickUpAddress!=null && deliverAddress!=null){
            Order orderToCreate = new Order();
            orderToCreate.setBuyer(currentUser.get());
            orderToCreate.setSeller(seller);
            if(request.getTransporterId()!=null ){
                Transporter transporter = transporterService.getById(request.getTransporterId());
                orderToCreate.setTransporter(transporter);
            }
            orderToCreate.setItems(orderItems);
            orderToCreate.setDeliverAddress(deliverAddress);
            orderToCreate.setPickUpAddress(pickUpAddress);
            orderToCreate.setStatus(OrderStatus.CREATED);

            return orderRepository.save(orderToCreate);
        }
        else{
            return null;
        }
    }

}
