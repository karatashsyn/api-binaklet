package com.binaklet.binaklet.services;


import com.binaklet.binaklet.dto.responses.cart.CartDto;
import com.binaklet.binaklet.config.JwtService;
import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.entities.Cart;
import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.enums.UserRole;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.dto.requests.auth.LoginRequest;
import com.binaklet.binaklet.dto.requests.auth.RegisterRequest;
import com.binaklet.binaklet.dto.responses.AuthenticationResponse;
import com.binaklet.binaklet.dto.responses.address.AddressDetailDTO;
import com.binaklet.binaklet.dto.responses.item.ItemDetailDTO;
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
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        Optional<User> checkedUser = userRepository.findByEmail(request.getEmail());
        if( checkedUser.isPresent() ){
            throw new ApiRequestException("Bu email kullanılmakta.");
        }
        else{
            var user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword())).role(UserRole.USER)
                    .phoneNumber(request.getPhoneNumber())
                    .build();
            User createdUser = userService.createUserWithEmptyCard(user);
            var token = jwtService.generateToken(user);
            AuthenticationResponse response = AuthenticationResponse.builder()
                    .token(token).user(createdUser)
                    .build();
            return ResponseEntity.ok(response);
        }

    }

    public ResponseEntity<MeDTO> getMe(){
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        List<Address> userAddresses = currentUser.getAddresses();
        List<AddressDetailDTO> addressDetailDTOs = userAddresses.stream()
                .map(address -> new AddressDetailDTO(address.getId(),address.getAddressText(), address.getIsUserDefault(),address.getAddressTitle(),address.getContactPhone()))
                .toList();

        List<Item> userItems = currentUser.getItems();
        List<ItemDetailDTO> itemDetailDTOs =  userItems.stream().map(item->
            ItemDetailDTO.build(item.getId(),item.getName(),item.getPrice(),item.getWidth(),item.getHeight(),item.getDepth(),item.getMass(),item.getBrand(),item.getStatus(),item.getDescription(),item.getImages(),item.getCategory())).toList();

        Cart userCart = currentUser.getCart();
        CartDto userCartDto = CartDto.build(userCart.getId(),itemDetailDTOs);

        MeDTO response =  MeDTO.build(currentUser.getId(),currentUser.getRole(),
                currentUser.getName(),currentUser.getEmail(),currentUser.getPhoneNumber(),addressDetailDTOs,userCartDto, itemDetailDTOs,currentUser.getCreatedDate());

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<AuthenticationResponse> login(LoginRequest request) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
            var foundUser = userRepository.findByEmail(request.getEmail()).orElseThrow();
            var token = jwtService.generateToken(foundUser);
            AuthenticationResponse response =  AuthenticationResponse.builder().token(token).user(foundUser).build();
            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e){
            return null;
        }


    }
}
