package com.binaklet.binaklet.services;


import com.binaklet.binaklet.config.JwtService;
import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.UserRole;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.mappers.UserMapper;
import com.binaklet.binaklet.repositories.ProfileRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.dto.requests.auth.LoginRequest;
import com.binaklet.binaklet.dto.requests.auth.RegisterRequest;
import com.binaklet.binaklet.dto.responses.AuthenticationResponse;
import com.binaklet.binaklet.dto.responses.auth.MeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ProfileRepository profileRepository;
    private final UserMapper userMapper;


    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        Optional<User> checkedUser = userRepository.findByEmail(request.getEmail());
        if( checkedUser.isPresent() ){
            throw new ApiRequestException("Bu email kullanılmakta.");
        }
        else{
            Profile userProfile = new Profile();
            userProfile.setName(request.getName());
            Profile newProfile = profileRepository.save(userProfile);
            var user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword())).role(UserRole.USER)
                    .phoneNumber(request.getPhoneNumber())
                    .profile(newProfile)
                    .build();

            userRepository.save(user);



            var token = jwtService.generateToken(user);
            AuthenticationResponse response = AuthenticationResponse.builder()
                    .token(token).user(userMapper.toMeDTO(user))
                    .build();
            return ResponseEntity.ok(response);
        }

    }

    public User getAuthenticatedUser(){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (currentUser.isEmpty()){
            throw new ApiRequestException("Yetkili Kullanıcı Bulunamadı.");
        }
        return currentUser.get();
    }

    public ResponseEntity<MeDTO> getMe(){
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return ResponseEntity.ok(userMapper.toMeDTO(currentUser));
    }

    public ResponseEntity<AuthenticationResponse> login(LoginRequest request) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
            var foundUser = userRepository.findByEmail(request.getEmail()).orElseThrow();
            var token = jwtService.generateToken(foundUser);
            AuthenticationResponse response =  AuthenticationResponse.builder().token(token).user(userMapper.toMeDTO(foundUser)).build();
            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e){
            return null;
        }


    }
}
