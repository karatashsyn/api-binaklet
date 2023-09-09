package com.binaklet.binaklet.controllers;


import com.binaklet.binaklet.requests.LoginRequest;
import com.binaklet.binaklet.requests.RegisterRequest;
import com.binaklet.binaklet.responses.AuthenticationResponse;
import com.binaklet.binaklet.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        AuthenticationResponse response = authService.register(request);
        if(response.getToken()!=null){
            return ResponseEntity.ok(authService.register(request));
        }
        else{
            return ResponseEntity.status(403).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
