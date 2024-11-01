package com.binaklet.binaklet.services;

import com.binaklet.binaklet.dto.requests.cart.AddToCartRequest;
import com.binaklet.binaklet.dto.requests.cart.RemoveItemFromCart;
import com.binaklet.binaklet.dto.responses.cart.CartDto;
import com.binaklet.binaklet.entities.Cart;
import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.mappers.CartMapper;
import com.binaklet.binaklet.mappers.ItemMapper;
import com.binaklet.binaklet.repositories.CartRepository;
import com.binaklet.binaklet.repositories.ItemRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserRepository userRepo;
    private final CartRepository cartRepo;
    private final ItemRepository itemRepo;
    private final AuthService authService;
    private final CartMapper cartMapper;

    public Cart createEmpty(){
        Cart cart = new Cart();
        List<Item> emptyItems = new ArrayList<>();
        cart.setItems(emptyItems);
        return cartRepo.save(cart);
    }
    public ResponseEntity<CartDto> addItemsToMyCart(AddToCartRequest request) {
            User currentUser = authService.getAuthenticatedUser();
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
            return ResponseEntity.ok(cartMapper.toCartDTO(updatedCart, currentUser));


    }

    public ResponseEntity<CartDto> removeItemFromMyCart(RemoveItemFromCart request){
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
            cartRepo.save(cartOfTheUser);
            return this.getMyCart();


    }


    public ResponseEntity<CartDto> clearUserCart(Long userId){
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
        return this.getMyCart();
    }




    public ResponseEntity<CartDto> getMyCart(){
        User currentUser = authService.getAuthenticatedUser();
        return ResponseEntity.ok(cartMapper.toCartDTO(currentUser.getCart(), currentUser));

    }
}
