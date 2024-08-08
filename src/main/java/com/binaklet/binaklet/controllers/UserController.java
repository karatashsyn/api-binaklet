package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.services.UserService;
import com.binaklet.binaklet.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return userService.getAll();
    }


    @GetMapping({"/{userId}"})
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }
}

