package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service

public class AddressService extends  BaseService<Address,Long>{
    public AddressService(JpaRepository<Address, Long> repository) {
        super(repository);
    }
}
