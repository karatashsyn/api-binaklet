package com.binaklet.binaklet.controllers;
import com.binaklet.binaklet.DTOs.BasicItemDto;
import com.binaklet.binaklet.DTOs.CartDto;
import com.binaklet.binaklet.entities.Cart;
import com.binaklet.binaklet.requests.AddToCartRequest;
import com.binaklet.binaklet.requests.RemoveItemFromCart;
import com.binaklet.binaklet.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping(value = {"/addItemToMyCart"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Cart> addItemToMyCart(@RequestBody AddToCartRequest request){
        System.out.println(request.toString());
        List<Long> itemIds = request.getItemIds();
        Cart updatedCart = cartService.addItemsToMyCart(itemIds);
        return ResponseEntity.ok().body(updatedCart);
    }

    @PostMapping(value = {"/removeItemFromCart"})
    ResponseEntity<Cart> removeItemFromCart(@RequestBody RemoveItemFromCart request){
        System.out.println(request.toString());
        Long itemId = request.getItemId();
        Cart updatedCart = cartService.removeItemFromMyCart(itemId);
        return ResponseEntity.ok().body(updatedCart);
    }


    @GetMapping({"/getMyCart"} )
    ResponseEntity<CartDto> addItemToMyCart(){
        CartDto userCart = cartService.getMyCart();
        return ResponseEntity.ok().body(userCart);
    }

}
