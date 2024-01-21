package com.binaklet.binaklet.controllers;
import com.binaklet.binaklet.entities.Cart;
import com.binaklet.binaklet.requests.AddToCartRequest;
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
        List<Long> itemIds = request.getItemIds();
        Cart updatedCart = cartService.addItemsToMyCart(itemIds);
        return ResponseEntity.ok().body(updatedCart);
    }

    //TODO: For admins
//    @GetMapping( )
//    ResponseEntity<List<Cart>> getAll(){
//        List<Cart> data = cartService.getAll();
//        return ResponseEntity.ok().body(data);
//    }

    @GetMapping({"/getMyCart"} )
    ResponseEntity<Cart> addItemToMyCart(){
        Cart updatedCart = cartService.getMyCart();
        return ResponseEntity.ok().body(updatedCart);
    }

}
