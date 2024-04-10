package com.binaklet.binaklet.services;

import com.binaklet.binaklet.DTOs.BasicUserDto;
import com.binaklet.binaklet.DTOs.CartDto;
import com.binaklet.binaklet.DTOs.ItemDetailDto;
import com.binaklet.binaklet.entities.Cart;
import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.repositories.CartRepository;
import com.binaklet.binaklet.repositories.ItemRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.requests.AddToCartRequest;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserRepository userRepo;
    private final CartRepository cartRepo;
    private final ItemRepository itemRepo;
    public Cart createEmpty(){
        Cart cart = new Cart();
        List<Item> emptyItems = new ArrayList<>();
        cart.setItems(emptyItems);
        return cartRepo.save(cart);
    }
    public Cart addItemsToMyCart(List<Long> itemIds){
        try {
            String email =  SecurityContextHolder.getContext().getAuthentication().getName();
            User currentUser = userRepo.findByEmail(email).orElse(null);
            assert currentUser != null;
            Cart cartOfTheUser = currentUser.getCart();
            List<Item> itemsOfCart = cartOfTheUser.getItems();
            List<Item> itemsToAdd = itemRepo.findAllById(itemIds);
            if(itemIds.isEmpty()){throw new ApiRequestException("En az bir ürün seçmelisiniz.");}
            if(itemsToAdd.size()!=itemIds.size()){throw new ApiRequestException("Eklenenmeye çalışılan ürünlerden bazıları sistemde yok.");}
            for (Item item:itemsToAdd) {
                if(itemsOfCart.contains(item)){throw  new ApiRequestException( item.getName()+ " Adlı ürün zaten sepette.");}
            }
            for (Item item: itemsToAdd) {
                if(currentUser.getItems().contains(item)){throw new ApiRequestException("Kullanıcı kendi ürününü sepetine ekleyemez");}
            }
            //If successful, add all items to current cart content
            itemsOfCart.addAll(itemsToAdd);
            cartOfTheUser.setItems(itemsOfCart);
            return cartRepo.save(cartOfTheUser);
        }
        catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }

    }

    public Cart removeItemFromMyCart(Long itemId){
        try {
            String email =  SecurityContextHolder.getContext().getAuthentication().getName();
            User currentUser = userRepo.findByEmail(email).orElse(null);
            assert currentUser != null;
            Cart cartOfTheUser = currentUser.getCart();
            List<Item> itemsOfCart = cartOfTheUser.getItems();

            if(itemsOfCart.isEmpty()){
                throw new ApiRequestException("Sepet zaten boş");
            }

            Optional<Item> itemToRemove = itemsOfCart.stream().filter(item->item.getId().equals(itemId)).findFirst();
            if(itemToRemove.isEmpty()){throw new ApiRequestException("Ürün bulunamadı");}
            itemsOfCart.remove(itemToRemove.get());
            //If successful, add all items to current cart content
            cartOfTheUser.setItems(itemsOfCart);
            return cartRepo.save(cartOfTheUser);
        }
        catch (Exception e){
            throw new ApiRequestException(e.getMessage());
        }

    }




//    public List<Cart> getAll(){
//        return cartRepo.findAll();
//    }

    public CartDto getMyCart(){
        String email =  SecurityContextHolder.getContext().getAuthentication().getName();
        Cart currentUserCart = userRepo.findByEmail(email).orElse(null).getCart();
        List<Item> cartItems = currentUserCart.getItems();
        List<ItemDetailDto> detailedCartItems = new ArrayList<>();
        for (Item item : cartItems) {
            User ownerOfItem = item.getUser();
            BasicUserDto itemUser = BasicUserDto.builder().id(ownerOfItem.getId()).email(ownerOfItem.getEmail()).firstName(ownerOfItem.getFirstName()).lastName(ownerOfItem.getLastName()).addresses(ownerOfItem.getAddresses().stream().map(adrs -> adrs.getAddressText()).collect(Collectors.toList())).build();
            ItemDetailDto itemDetail = ItemDetailDto.builder().id(item.getId()).name(item.getName()).price(item.getPrice()).mass(item.getMass()).brand(item.getBrand()).age(item.getAge()).status(item.getStatus()).description(item.getDescription()).images(item.getImages()).type(item.getItemType()).height(item.getHeight()).width(item.getWidth()).depth(item.getDepth()).owner(itemUser).build();
            detailedCartItems.add(itemDetail);
        }

        return CartDto.builder().id(currentUserCart.getId()).items(detailedCartItems).build();

    }
}
