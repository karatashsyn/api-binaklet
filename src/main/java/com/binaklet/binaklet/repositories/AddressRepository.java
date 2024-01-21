package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByUser(User user);
}
