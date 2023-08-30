package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.services.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private AddressService addressService;

    public AddressController (AddressService addressService){
        this.addressService=addressService;
    }

    @GetMapping
    public List<Address> getAddresses(){
        return addressService.getAll();
    }

    @PostMapping()
    public Address createAddress(@RequestBody Address newAddress, @RequestBody String body){
        return addressService.create(newAddress);
    }

    @GetMapping({"/{addressId}"})
    public Address getAddress(@PathVariable Long addressId){
        return addressService.getById(addressId);
    }

    @PostMapping({"/{addressId}"})
    public Address updateAddress(@PathVariable Long addressId, @RequestBody Address newAddress){
        return addressService.update(addressId, newAddress);
    }
}
