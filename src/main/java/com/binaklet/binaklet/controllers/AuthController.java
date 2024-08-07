package com.binaklet.binaklet.controllers;


import dto.requests.auth.LoginRequest;
import dto.requests.auth.RegisterRequest;
import com.binaklet.binaklet.responses.AuthenticationResponse;
import com.binaklet.binaklet.services.AuthService;
import dto.responses.user.MeDTO;
import dto.responses.user.UserDetailDTO;
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
        AuthenticationResponse response = authService.register(request);
        if(response!=null && response.getToken()!=null){
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.status(403).body(response);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<MeDTO> getMe(){
        return ResponseEntity.status(200).body(authService.getMe()) ;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request){
        AuthenticationResponse response = authService.login(request);
        if(response!=null&& response.getToken()!=null && !response.getToken().isBlank()){
            return ResponseEntity.ok(authService.login(request));
        }
        else{
            return ResponseEntity.status(403).body(null);
        }
    }
}
