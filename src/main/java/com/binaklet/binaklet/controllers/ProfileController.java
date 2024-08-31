package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.dto.requests.profile.UpdateProfileRequest;
import com.binaklet.binaklet.entities.Profile;
import com.binaklet.binaklet.services.ProfileService;
import com.binaklet.binaklet.services.UserService;
import com.binaklet.binaklet.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Validated
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<Profile> getMyProfile() {
        return profileService.getMyProfile();
    }

    @PatchMapping
    public ResponseEntity<Profile> updateProfile(@Valid @ModelAttribute UpdateProfileRequest updateProfileRequest){
        return profileService.updateMyProfile(updateProfileRequest);
    }

}