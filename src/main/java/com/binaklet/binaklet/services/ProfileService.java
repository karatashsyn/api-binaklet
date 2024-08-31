package com.binaklet.binaklet.services;

import com.binaklet.binaklet.dto.requests.profile.UpdateProfileRequest;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.repositories.ProfileRepository;
import com.binaklet.binaklet.repositories.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.binaklet.binaklet.entities.Profile;
import com.binaklet.binaklet.entities.User;

@Service
@RequiredArgsConstructor
public class ProfileService  {
    private final ProfileRepository profileRepository;
    private  final UserRepository userRepository;

   public ResponseEntity<Profile> getMyProfile(){
    User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new ApiRequestException("Yetkili kullanıcı bulunamadı"));
    return ResponseEntity.ok(currentUser.getProfile());
   }

   public ResponseEntity<Profile> updateMyProfile(UpdateProfileRequest request){
       User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new ApiRequestException("Yetkili kullanıcı bulunamadı"));
       Profile userProfile = currentUser.getProfile();
       userProfile.setName(request.getName());
        return ResponseEntity.ok(profileRepository.save(userProfile));
   }
}