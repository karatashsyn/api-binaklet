package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.services.UserService;
import com.binaklet.binaklet.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me")
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getMe() {
        return userService.getMe();
    }

}