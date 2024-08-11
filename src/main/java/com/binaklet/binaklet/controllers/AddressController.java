package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.dto.requests.address.CreateAddressRequest;
import com.binaklet.binaklet.services.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Address>> getMyAddresses(){
        return addressService.getCurrentUserAddresses();
    }

    @PostMapping()
    public ResponseEntity<Address> addAddress(@RequestBody @Valid CreateAddressRequest request){
        return addressService.create(request);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<List<Address>> deleteAddress(@PathVariable(name = "addressId") Long addressId){
        return addressService.deleteAddress(addressId);
    }

    @PostMapping("/{addressId}/makeDefault")
    public ResponseEntity<Address> makeDefault(@PathVariable(name = "addressId") Long addressId){
        return addressService.makeAddressDefault(addressId);
    }

    @GetMapping({"/{addressId}"})
    public ResponseEntity<Address> getAddress(@PathVariable Long addressId){
        return addressService.getById(addressId);

    }

}
