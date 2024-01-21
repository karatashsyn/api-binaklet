package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.repositories.AddressRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.requests.AddressCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
@RequiredArgsConstructor
public class AddressService{
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    public Address create(AddressCreateRequest request) {
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili Kullanıcı Buluamadı");}
        Address addressToSave = new Address();
        addressToSave.setUser(currentUser.get());
        addressToSave.setAddressText(request.getAddressText());
        return addressRepository.save(addressToSave);
    }

    public Address getById(Long addressId) {
        Optional<Address> foundAddress = addressRepository.findById(addressId);
        if(foundAddress.isEmpty()){throw new ApiRequestException("Address bulunamadı");}
        return foundAddress.get();
    }

    //TODO: Remove since unsecure.
    public List<Address> getAll(){ return addressRepository.findAll();}

    public List<Address> getCurrentUserAddresses() {
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili Kullanıcı Buluamadı");}
        List<Address> foundAddresses = addressRepository.findByUser(currentUser.get());
        if(foundAddresses.isEmpty()){throw new ApiRequestException("Address bulunamadı");}
        return foundAddresses;
    }
}
