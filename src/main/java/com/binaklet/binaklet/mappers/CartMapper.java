package com.binaklet.binaklet.mappers;

import com.binaklet.binaklet.dto.responses.cart.CartDto;
import com.binaklet.binaklet.dto.responses.item.ItemDetailDTO;
import com.binaklet.binaklet.entities.Cart;
import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final ItemMapper itemMapper;
    public CartDto toCartDTO(Cart cart, User user){
        List<Item> cartItems = cart.getItems();
        Map<Long, List<ItemDetailDTO>> userCart = new HashMap<>();

        for (Item item : cartItems) {
            User seller = item.getUser();
            Long sellerId = seller.getId();
            ItemDetailDTO itemDetailDTO = itemMapper.toItemDetailDTO(item, user);
            userCart.computeIfAbsent(sellerId, k -> new ArrayList<>()).add(itemDetailDTO);
        }

        return CartDto.build(cart.getId(), userCart, cartItems.size(), cartItems.stream().map(Item::getId).toList());
    }

}
