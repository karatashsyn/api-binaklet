package com.binaklet.binaklet.EntityListeners;

import com.binaklet.binaklet.entities.Cart;
import com.binaklet.binaklet.entities.Profile;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.repositories.CartRepository;
import com.binaklet.binaklet.repositories.ProfileRepository;
import jakarta.persistence.PostPersist;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(force = true)
public class UserEntityListener {
    private static CartRepository cartRepository;


    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        UserEntityListener.cartRepository = cartRepository;
    }

    @PostPersist
    public void createCartForUser(User user) {
        Cart cart = new Cart();
        user.setCart(cart);
        cartRepository.save(cart);
    }
}
