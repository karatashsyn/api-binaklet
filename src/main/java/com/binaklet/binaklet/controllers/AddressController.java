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
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {


    private final AddressService addressService;
    private  final UserService userService;
    private final UserRepository userRepo;


    @GetMapping
    public List<Address> getMyAddresses(){
        return addressService.getCurrentUserAddresses();
    }

    @PostMapping()
    public Address createAddress(@RequestBody AddressCreateRequest request){
        return addressService.create(request);
    }

    @GetMapping({"/{addressId}"})
    public Address getAddress(@PathVariable Long addressId){
        return addressService.getById(addressId);
    }

}
