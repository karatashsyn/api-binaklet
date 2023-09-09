package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.requests.AddressCreateRequest;
import com.binaklet.binaklet.services.AddressService;
import com.binaklet.binaklet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private AddressService addressService;

    private UserService userService;

    public AddressController (AddressService addressService,UserService userService){
        this.addressService=addressService;
        this.userService = userService;
    }

    @GetMapping
    public List<Address> getAddresses(){
        return addressService.getAll();
    }

    @PostMapping()
    public Address createAddress(@RequestBody AddressCreateRequest newAddress){
        System.out.print(newAddress.toString());
        Long userId = newAddress.getUserId();
        User user = userService.getById(userId);
        Address addressToSave = new Address();
        if(user!=null){
            addressToSave.setUser(user);
            addressToSave.setAddressText(newAddress.getAddressText());
            return addressService.create(addressToSave);
        }
        else{
            return null;
        }

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
