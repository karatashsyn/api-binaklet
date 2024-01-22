package com.binaklet.binaklet.controllers;


import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserController {
    private final UserService userService;
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}
