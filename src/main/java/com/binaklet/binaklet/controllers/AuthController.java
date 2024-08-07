package com.binaklet.binaklet.controllers;


import com.binaklet.binaklet.dto.requests.auth.LoginRequest;
import com.binaklet.binaklet.dto.requests.auth.RegisterRequest;
import com.binaklet.binaklet.dto.responses.AuthenticationResponse;
import com.binaklet.binaklet.services.AuthService;
import com.binaklet.binaklet.dto.responses.auth.MeDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request){
        return authService.register(request);
    }

    @GetMapping("/me")
    public ResponseEntity<MeDTO> getMe(){
        return authService.getMe();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request){
        return authService.login(request);
    }
}
