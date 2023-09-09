package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
