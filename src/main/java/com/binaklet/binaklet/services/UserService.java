package com.binaklet.binaklet.services;

import com.binaklet.binaklet.enums.UserStatus;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepo;
    private final CartService cartService;

    public User getById(Long id){
        User foundUser  = userRepo.findById(id).orElse(null);
        if(foundUser==null || foundUser.getStatus() == UserStatus.DELETED) throw new ApiRequestException("Kullanıcı Bulunamadı");

        return foundUser;
    }

    public ResponseEntity<List<User>> getAll()
    {
        return ResponseEntity.ok(userRepo.findAll());
    }

    public ResponseEntity<User> getMe(){
        Optional<User> currentUser = userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        return ResponseEntity.ok(currentUser.get());
    }
}
