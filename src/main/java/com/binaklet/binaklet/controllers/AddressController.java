package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.repositories.UserRepository;
import dto.requests.address.CreateAddressRequest;
import com.binaklet.binaklet.services.AddressService;
import com.binaklet.binaklet.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@Validated
public class AddressController {


    private final AddressService addressService;


    @GetMapping
    public List<Address> getMyAddresses(){
        return addressService.getCurrentUserAddresses();
    }

    @PostMapping()
    public Address createAddress(@RequestBody @Valid CreateAddressRequest request){
        return addressService.create(request);
    }

    @GetMapping({"/{addressId}"})
    public Address getAddress(@PathVariable Long addressId){
        return addressService.getById(addressId);
    }

}
