package com.binaklet.binaklet.services;

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


//    public List<Cart> getAll(){
//        return cartRepo.findAll();
//    }

    public Cart getMyCart(){
        String email =  SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findByEmail(email).orElse(null);
        assert currentUser != null;
        return currentUser.getCart();
    }
}
