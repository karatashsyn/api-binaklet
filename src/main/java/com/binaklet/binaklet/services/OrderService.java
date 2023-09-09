package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Order;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.repositories.OrderRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService extends BaseService<Order,Long>{

    OrderRepository orderRepository;
    public OrderService(JpaRepository<Order, Long> repository,OrderRepository orderRepository) {
        super(repository);
        this.orderRepository = orderRepository;
    }

}
