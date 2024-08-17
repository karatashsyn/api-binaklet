package com.binaklet.binaklet.services;

import com.binaklet.binaklet.dto.requests.cart.AddToCartRequest;
import com.binaklet.binaklet.dto.requests.cart.RemoveItemFromCart;
import com.binaklet.binaklet.dto.responses.cart.CartDto;
import com.binaklet.binaklet.dto.responses.item.ItemDetailDTO;
import com.binaklet.binaklet.dto.responses.user.BasicUserDto;
import com.binaklet.binaklet.entities.Address;
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

import java.util.*;

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
        Optional<User> currentUser = userRepo.findByEmail(email);
        if(currentUser.isEmpty()) {throw new ApiRequestException("Yetkili kullanıcı bulunamadı.");}
        Cart currentUserCart = currentUser.get().getCart();

        List<Item> cartItems = currentUserCart.getItems();
        List<ItemDetailDTO> detailedCartItems = new ArrayList<>();

        Map<Long, List<ItemDetailDTO>> userCart = new HashMap<>();

        for (Item item : cartItems) {
            User seller = item.getUser();
            Long sellerId = seller.getId();

            boolean isFavourite = currentUser.get().getFavourites().contains(item);

            BasicUserDto sellerDTO = BasicUserDto.build(sellerId,seller.getEmail(),seller.getProfile().getName(),seller.getProfile().getAvatar(),seller.getRating(),seller.getRateCount(),seller.getAddresses().stream().map(Address::getAddressText).toList());
            ItemDetailDTO itemDetailDTO = ItemDetailDTO.build(item.getId(), item.getName(), item.getPrice(), item.getHeight(), item.getWidth(), item.getDepth(), item.getMass(), item.getBrand(), item.getStatus(), item.getDescription(), item.getImages(), item.getCategory(),sellerDTO,isFavourite);

            List<ItemDetailDTO> sellerItems = userCart.get(sellerId);

//            if(sellerItems==null){
//                List<ItemDetailDTO> freshSellerItems = new ArrayList<>();
//                freshSellerItems.add(itemDetailDTO);
//                userCart.put(sellerId,freshSellerItems);
//            }
//            else{
//                sellerItems.add(itemDetailDTO);
//            }
            userCart.computeIfAbsent(sellerId, k -> new ArrayList<>()).add(itemDetailDTO);

        }
        CartDto cartResponse = CartDto.build(currentUserCart.getId(), userCart);

        return ResponseEntity.ok(cartResponse);

    }
}
