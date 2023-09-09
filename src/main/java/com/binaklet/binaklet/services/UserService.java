package com.binaklet.binaklet.services;

import com.binaklet.binaklet.repositories.OrderRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.entities.User;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService extends BaseService<User,Long>{

    public UserService(JpaRepository<User, Long> repository) {

        super(repository);
    }
}
