package com.binaklet.binaklet.services.admin;

import com.binaklet.binaklet.DTOs.AdminOrderDto;
import dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.DTOs.BasicUserDto;
import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.enums.OrderStatus;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.repositories.ItemRepository;
import com.binaklet.binaklet.repositories.OrderRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import dto.requests.Admin.AdminOrderCreateRequest;
import com.binaklet.binaklet.services.AddressService;
import com.binaklet.binaklet.services.ItemService;
import com.binaklet.binaklet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminOrderService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final AddressService addressService;
    private final UserService userService;
    private final ItemService itemService;

    public List<AdminOrderDto> getAll(){
        List<Order> orders  = orderRepository.findAll();
        List<AdminOrderDto> resultOrders = new ArrayList<>();
        for (Order order :orders) {
            List<BasicItemDTO> orderItems = new ArrayList<>();
            for (Item item : order.getItems()){
                List<String> itemImages = item.getImages().stream().map(Image::getUrl).toList();
                orderItems.add(BasicItemDTO.builder().images(itemImages).name(item.getName()).price(item.getPrice()).build());
            }
            AdminOrderDto dto = AdminOrderDto.builder()
                    .id(order.getId())
                    .seller(BasicUserDto.builder().email(order.getSeller().getEmail()).id(order.getSeller().getId()).build())
                    .buyer(BasicUserDto.builder().email(order.getBuyer().getEmail()).id(order.getBuyer().getId()).build())
                    .deliverAddress(order.getDeliverAddress())
                    .pickUpAddress(order.getPickUpAddress())
                    .payment(order.getPayment())
                    .status(order.getStatus())
                    .createdDate(order.getCreatedDate())
                    .items(orderItems)
                    .build();
            resultOrders.add(dto);
        }
        return resultOrders;
    }


    public Order create (AdminOrderCreateRequest request){
        String pickUpAddress = request.getPickUpAddress();
        String deliverAddress = request.getDeliverAddress();

        Long[] itemsId = request.getItemIds();
        List<Item> orderItems = new ArrayList<>();
        for (Long id :itemsId
        ) {
            Item foundItem = itemService.get(id);

            if(foundItem.getId()==null){
                throw new ApiRequestException("Eklemek istenilen ürün sistemde bulunmamaktadır");
            }
            if( foundItem.getStatus()== ItemStatus.SOLD || foundItem.getStatus()==ItemStatus.INACTIVE){
                throw new ApiRequestException("Eklemek istenilen ürün satın alınmış veya inaktif.");
            }
            orderItems.add(foundItem);
        }
        User seller = userService.getById(request.getSellerId());
        User buyer = userService.getById(request.getBuyerId());
        if(orderItems.isEmpty()){throw new ApiRequestException("Sipariş en az bir ürün içermeli");}
        if(seller==null){throw new ApiRequestException("Satıcı bulunamadı");}
        if(buyer==null) {throw new ApiRequestException("Alıcı bulunamadı");}
        if(pickUpAddress==null){throw new ApiRequestException("Alış adresi bulunamadı");}
        if(deliverAddress==null){throw new ApiRequestException("Teslim adresi bulunamadı");}
        Order orderToCreate = new Order();
        orderToCreate.setBuyer(buyer);
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
    public Order getById(Long orderId){
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if(foundOrder.isEmpty())throw new ApiRequestException("Geçersiz Sipariş Id");
        return foundOrder.get();
    }

    public List<Order> getUserOrders(Long userId){
        Optional<User> foundUser = userRepository.findById(userId);
        if(foundUser.isEmpty()) throw new ApiRequestException("Geçersiz Kulanıcı Id");
        return orderRepository.findOrdersByBuyer(foundUser.get());
    }

    public void deleteOrderById(Long orderId){
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if(foundOrder.isEmpty())throw new ApiRequestException("Geçersiz Sipariş Id");
        orderRepository.delete(foundOrder.get());
    }
}
