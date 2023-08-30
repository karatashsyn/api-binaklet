package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
