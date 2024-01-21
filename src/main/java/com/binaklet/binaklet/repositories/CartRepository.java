package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
