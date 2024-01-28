package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Order;
import com.binaklet.binaklet.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    public List<Order> findOrdersByBuyer(User buyer);
}
