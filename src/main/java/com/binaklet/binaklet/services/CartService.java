package com.binaklet.binaklet.services;

import com.binaklet.binaklet.dto.requests.cart.AddToCartRequest;
import com.binaklet.binaklet.dto.requests.cart.RemoveItemFromCart;
import com.binaklet.binaklet.dto.responses.user.BasicUserDto;
import com.binaklet.binaklet.dto.responses.cart.CartDto;
import com.binaklet.binaklet.dto.responses.item.ItemDetailDTO;
import com.binaklet.binaklet.entities.Cart;
import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.repositories.CartRepository;
import com.binaklet.binaklet.repositories.ItemRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Cart> addItemsToMyCart(AddToCartRequest request) {
            String email =  SecurityContextHolder.getContext().getAuthentication().getName();
            User currentUser = userRepo.findByEmail(email).orElse(null);
            assert currentUser != null;
            List<Long> itemIds = request.getItemIds();
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
            Cart updatedCart = cartRepo.save(cartOfTheUser);
            return ResponseEntity.ok(updatedCart);


    }

    public ResponseEntity<Cart> removeItemFromMyCart(RemoveItemFromCart request){
            Long itemId = request.getItemId();
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

            cartOfTheUser.setItems(itemsOfCart);
            Cart updatedCart =cartRepo.save(cartOfTheUser);
            return ResponseEntity.ok(updatedCart);


    }


    public void clearUserCart(Long userId){
        User user = userRepo.findById(userId).orElse(null);
        if(user==null){throw new ApiRequestException("Kullanıcı bulunamadı");}
        Cart cartOfTheUser = user.getCart();

        List<Item> itemsOfCart = cartOfTheUser.getItems();

        if(itemsOfCart.isEmpty()){
            throw new ApiRequestException("Sepet zaten boş");
        }
        Cart cart = user.getCart();
        cart.setItems(Collections.emptyList());
        cartRepo.save(cart);
    }




    public ResponseEntity<CartDto> getMyCart(){
        String email =  SecurityContextHolder.getContext().getAuthentication().getName();
        Cart currentUserCart = userRepo.findByEmail(email).orElse(null).getCart();
        List<Item> cartItems = currentUserCart.getItems();
        List<ItemDetailDTO> detailedCartItems = new ArrayList<>();
        for (Item item : cartItems) {
            User ownerOfItem = item.getUser();
            BasicUserDto itemUser = BasicUserDto.builder().id(ownerOfItem.getId()).email(ownerOfItem.getEmail()).name(ownerOfItem.getName()).addresses(ownerOfItem.getAddresses().stream().map(adrs -> adrs.getAddressText()).collect(Collectors.toList())).build();
            ItemDetailDTO itemDetail = ItemDetailDTO.builder().id(item.getId()).name(item.getName()).price(item.getPrice()).mass(item.getMass()).brand(item.getBrand()).status(item.getStatus()).description(item.getDescription()).images(item.getImages()).type(item.getCategory()).height(item.getHeight()).width(item.getWidth()).depth(item.getDepth()).build();
            detailedCartItems.add(itemDetail);
        }
        CartDto userCart = CartDto.builder().id(currentUserCart.getId()).items(detailedCartItems).build();

        return ResponseEntity.ok(userCart);

    }
}
