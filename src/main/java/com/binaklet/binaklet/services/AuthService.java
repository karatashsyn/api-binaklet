package com.binaklet.binaklet.services;


import com.binaklet.binaklet.config.JwtService;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.enums.UserRole;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.requests.LoginRequest;
import com.binaklet.binaklet.requests.RegisterRequest;
import com.binaklet.binaklet.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> checkedUser = userRepository.findByEmail(request.getEmail());
        if( checkedUser.isPresent() ){
            System.out.println("already in use" + checkedUser.toString());
            return AuthenticationResponse.builder().token(null).message("Bu email kullanılmakta.").build();
        }
        else{
            System.out.println("Registering new user. ");
            var user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword())).role(UserRole.USER)
                    .phoneNumber(request.getPhoneNumber())
                    .build();
            User createdUser = userService.createUserWithEmptyCard(user);
            var token = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(token).user(createdUser)
                    .build();
        }

    }

    public AuthenticationResponse login(LoginRequest request) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
            var foundUser = userRepository.findByEmail(request.getEmail()).orElseThrow();
            var token = jwtService.generateToken(foundUser);
            return AuthenticationResponse.builder().token(token).user(foundUser).build();
        }
        catch (AuthenticationException e){
            return null;
        }


    }
}
