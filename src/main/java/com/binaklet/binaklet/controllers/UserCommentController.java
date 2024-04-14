package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.entities.UserComment;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.requests.AddressCreateRequest;
import com.binaklet.binaklet.requests.UserCommentCreateRequest;
import com.binaklet.binaklet.services.AddressService;
import com.binaklet.binaklet.services.UserCommentService;
import com.binaklet.binaklet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class UserCommentController {


    private final UserCommentService userCommentService;
    private  final UserService userService;
    private final UserRepository userRepo;

    @GetMapping
    public List<UserComment> getAll(){
        return userCommentService.getAll();
    }

    @PostMapping
    public UserComment create(@RequestBody UserCommentCreateRequest req){
        return userCommentService.create(req);
    }


}
