package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.requests.AddressCreateRequest;
import com.binaklet.binaklet.services.AddressService;
import com.binaklet.binaklet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/addresses")
@RequiredArgsConstructor
public class AdminAddressController {


    private final AddressService addressService;



    @GetMapping
    public List<Address> getAll(){
        return addressService.getAll();
    }

}
