package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.repositories.AddressRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.dto.requests.address.CreateAddressRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService{
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    public ResponseEntity<Address> create(@RequestBody @Valid CreateAddressRequest request) {
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (currentUser.isEmpty()) {
            throw new ApiRequestException("Yetkili Kullanıcı Bulunamadı.");
        }
        Address addressToSave = new Address();
        if(currentUser.get().getAddresses().isEmpty()){
            addressToSave.setIsUserDefault(true);
        }
        addressToSave.setUser(currentUser.get());
        addressToSave.setAddressTitle(request.getAddressTitle());
        addressToSave.setAddressText(request.getAddressText());
        addressToSave.setContactPhone(request.getContactPhone());

        Address newAddress = addressRepository.save(addressToSave);
        return ResponseEntity.ok(newAddress);
    }

    public ResponseEntity<Address> getById(Long addressId) {
        Optional<Address> foundAddress = addressRepository.findById(addressId);
        if(foundAddress.isEmpty()){throw new ApiRequestException("Address bulunamadı.");}

        return ResponseEntity.ok(foundAddress.get());
    }

    public ResponseEntity<Address> makeAddressDefault(Long addressId){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (currentUser.isEmpty()) {
            throw new ApiRequestException("Yetkili Kullanıcı Bulunamadı.");
        }
        Optional<Address> targetAddress = addressRepository.findById(addressId);
        if(targetAddress.isEmpty()){throw new ApiRequestException("Address bulunamadı.");}
        if( !currentUser.get().getAddresses().contains(targetAddress.get())){
            throw new ApiRequestException("Bu adres size ait değil.");
        }
        List<Address> userAddresses = currentUser.get().getAddresses();
        Optional<Address> previousDefault = userAddresses.stream().filter(address -> address.getIsUserDefault().equals(true)).findFirst();
        if(previousDefault.isPresent()) {
            previousDefault.get().setIsUserDefault(false);
            addressRepository.save(previousDefault.get());
        }
        targetAddress.get().setIsUserDefault(true);
        return ResponseEntity.ok(addressRepository.save(targetAddress.get()));

    }


    public ResponseEntity<List<Address>> getCurrentUserAddresses() {
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Address> foundAddresses = addressRepository.findByUser(currentUser.get());
        return ResponseEntity.ok(foundAddresses);
    }

    public ResponseEntity<List<Address>> deleteAddress(Long addressId) {
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Address> foundAddresses = addressRepository.findByUser(currentUser.get());
        if(foundAddresses.stream().map(Address::getId).toList().contains(addressId)){
            Optional<Address> targetAddress = addressRepository.findById(addressId);
            if(targetAddress.isEmpty()){throw new ApiRequestException("Adres bulunamadı");}
            foundAddresses.remove(targetAddress.get());
            currentUser.get().setAddresses(foundAddresses);
            userRepository.save(currentUser.get());
            return ResponseEntity.ok(foundAddresses);
        }
        else{
            throw new ApiRequestException("Bu id ile size ait bir adres bulunamadı.");
        }
    }


}
