package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Cart;
import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.repositories.CartRepository;
import com.binaklet.binaklet.repositories.OrderRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.entities.User;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepo;
    private final CartService cartService;
    public User createUserWithEmptyCard(User user) {
        Cart newCart = cartService.createEmpty();
        user.setCart(newCart);
        return userRepo.save(user);
    }

    public User getById(Long id){
        return userRepo.findById(id).orElse(null);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }
}
