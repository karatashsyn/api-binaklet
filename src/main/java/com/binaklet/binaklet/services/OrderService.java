package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.enums.OrderStatus;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.repositories.AddressRepository;
import com.binaklet.binaklet.repositories.OrderRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.dto.requests.order.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final CartService cartService;
    private final AddressRepository addressRepository;


    public ResponseEntity<Order> create (CreateOrderRequest request){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}

        Optional<Address> pickUpAddress =addressRepository.findById(request.getPickupAddressId());
        Optional<Address> deliverAddress =addressRepository.findById(request.getDeliverAddressId());

        if(deliverAddress.isEmpty()) throw new ApiRequestException("Varış adresi sistemde bulunmamaktadır.");
        if(pickUpAddress.isEmpty()) throw new ApiRequestException("Çıkış adresi sistemde bulunmamaktadır.");


        List<Item> orderItems = new ArrayList<>();
        for (Long id :request.getItemIds()
        ) {
            Item foundItem = itemService.get(id);

            if(foundItem.getId()==null){
                throw new ApiRequestException("Eklemek istenilen ürün sistemde bulunmamaktadır");
            }
            if( foundItem.getStatus()==ItemStatus.SOLD || foundItem.getStatus()==ItemStatus.INACTIVE){
                throw new ApiRequestException("Eklemek istenilen ürün satın alınmış veya inaktif.");
            }
            orderItems.add(foundItem);
        }
        User seller = userRepository.getById(request.getSellerId());
        if(orderItems.isEmpty()){throw new ApiRequestException("Sipariş en az bir ürün içermeli");}
        if(seller==null){throw new ApiRequestException("Satıcı bulunamadı");}
        Order orderToCreate = new Order();
        orderToCreate.setBuyer(currentUser.get());
        orderToCreate.setSeller(seller);
        orderToCreate.setItems(orderItems);
        orderToCreate.setDeliverAddress(deliverAddress.get());
        orderToCreate.setPickUpAddress(pickUpAddress.get());
        orderToCreate.setStatus(OrderStatus.CREATED);
        Order savedOrder =  orderRepository.save(orderToCreate);
        for (Item item :orderItems) {
            item.setStatus(ItemStatus.SOLD);
            itemService.assignToOrder(item,savedOrder);
        }
        cartService.clearUserCart( currentUser.get().getId());
        return ResponseEntity.ok(savedOrder);
    }

    public ResponseEntity<List<Order>> getMyOrders(){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili Kullanıcı Bulunamadı");}
        return ResponseEntity.ok( currentUser.get().getOrders());

    }

}
