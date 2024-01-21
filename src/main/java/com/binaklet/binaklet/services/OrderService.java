package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.enums.OrderStatus;
import com.binaklet.binaklet.exceptions.ApiRequestException;
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
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili Kullanıcı Bulunamadı");}
        Address pickUpAddress = addressService.getById(request.getPickUpAddressId());
        Address deliverAddress = addressService.getById(request.getDeliverAddressId());

        Long[] itemsId = request.getItemIds();
        List<Item> orderItems = new ArrayList<>();
        for (Long id :itemsId
        ) {
            Item foundItem = itemService.getById(id);

            if(foundItem.getId()==null){
                throw new ApiRequestException("Eklemek istenilen ürün sistemde bulunmamaktadır");
            }
            if( foundItem.getStatus()==ItemStatus.SOLD || foundItem.getStatus()==ItemStatus.INACTIVE){
                throw new ApiRequestException("Eklemek istenilen ürün satın alınmış veya inaktif.");
            }
            orderItems.add(foundItem);
        }
        User seller = userService.getById(request.getSellerId());
        if(orderItems.isEmpty()){throw new ApiRequestException("Sipariş en az bir ürün içermeli");}
        if(seller==null){throw new ApiRequestException("Satıcı bulunamadı");}
        if(pickUpAddress==null){throw new ApiRequestException("Alış adresi bulunamadı");}
        if(deliverAddress==null){throw new ApiRequestException("Teslim adresi bulunamadı");}
        Order orderToCreate = new Order();
        orderToCreate.setBuyer(currentUser.get());
        orderToCreate.setSeller(seller);
        orderToCreate.setItems(orderItems);
        orderToCreate.setDeliverAddress(deliverAddress);
        orderToCreate.setPickUpAddress(pickUpAddress);
        orderToCreate.setStatus(OrderStatus.CREATED);
        Order savedOrder =  orderRepository.save(orderToCreate);
        for (Item item :orderItems) {
            itemService.assignToOrder(item,savedOrder);
        }
        return savedOrder;
    }

}
