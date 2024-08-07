package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.repositories.AddressRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import dto.requests.address.CreateAddressRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService{
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    public Address create(@RequestBody @Valid CreateAddressRequest request) {
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Address addressToSave = new Address();

        addressToSave.setUser(currentUser.get());
        addressToSave.setAddressTitle(request.getAddressTitle());
        addressToSave.setAddressText(request.getAddressText());
        addressToSave.setContactPhone(request.getContactPhone());

        return addressRepository.save(addressToSave);
    }

    public Address getById(Long addressId) {
        Optional<Address> foundAddress = addressRepository.findById(addressId);
        if(foundAddress.isEmpty()){throw new ApiRequestException("Address bulunamadı");}
        return foundAddress.get();
    }


    public List<Address> getCurrentUserAddresses() {
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Address> foundAddresses = addressRepository.findByUser(currentUser.get());
        if(foundAddresses.isEmpty()){throw new ApiRequestException("Address bulunamadı");}
        return foundAddresses;
    }
}
