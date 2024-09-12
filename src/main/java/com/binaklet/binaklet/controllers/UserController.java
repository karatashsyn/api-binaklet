package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.dto.responses.user.BasicUserDto;
import com.binaklet.binaklet.dto.responses.user.UserDetailDTO;
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
    public ResponseEntity<UserDetailDTO> getUser(@PathVariable Long userId) {
        return userService.getUserDetail(userId);
    }

    @PostMapping({"/follow/{userId}"})
    public ResponseEntity<BasicUserDto> followUser(@PathVariable Long userId){
        return userService.followUser(userId);
    }

    @PostMapping({"/unfollow/{userId}"})
    public ResponseEntity<BasicUserDto> unfollowUser(@PathVariable Long userId){
        return userService.unfollowUser(userId);
    }
}

