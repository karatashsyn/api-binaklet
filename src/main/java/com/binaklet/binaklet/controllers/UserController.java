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
@RequestMapping("/api/users")
public class UserController {



    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers()
    {
        List<User> users = userService.getAll();
        return ResponseEntity.ok().body(users);
    }


    @GetMapping({"/{userId}"})
    public User getUser(@PathVariable Long userId){
        return userService.getById(userId);
    }

//    @PostMapping({"/{userId}"})
//    public User updateUser(@PathVariable Long userId, @RequestBody User newUser){
//        return userService.update(userId,newUser);
//    }
}
