package com.binaklet.binaklet.controllers;
import com.binaklet.binaklet.dto.responses.cart.CartDto;
import com.binaklet.binaklet.entities.Cart;
import com.binaklet.binaklet.dto.requests.cart.AddToCartRequest;
import com.binaklet.binaklet.dto.requests.cart.RemoveItemFromCart;
import com.binaklet.binaklet.services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/carts")
@RequiredArgsConstructor
@Validated
public class CartController {
    private final CartService cartService;
    @PostMapping(value = {"/addItemToMyCart"},consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CartDto> addItemToMyCart( @Valid @RequestBody AddToCartRequest request){
        return cartService.addItemsToMyCart(request);
    }

    @PostMapping(value = {"/removeItemFromCart"})
    ResponseEntity<CartDto> removeItemFromCart(@Valid @RequestBody RemoveItemFromCart request){
        return cartService.removeItemFromMyCart(request);
    }

    @GetMapping({"/getMyCart"} )
    ResponseEntity<CartDto> getMyCart(){
        return cartService.getMyCart();
    }

}
