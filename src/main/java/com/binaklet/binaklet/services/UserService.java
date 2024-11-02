package com.binaklet.binaklet.services;

import com.binaklet.binaklet.dto.responses.user.BasicUserDto;
import com.binaklet.binaklet.dto.responses.user.UserDetailDTO;
import com.binaklet.binaklet.enums.UserStatus;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.mappers.UserMapper;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepo;
    private final AuthService authService;
    private final UserMapper userMapper;

    public ResponseEntity<UserDetailDTO> getUserDetail(Long id){
        User currentUser = authService.getAuthenticatedUser();
        User foundUser  = userRepo.findById(id).orElse(null);
        if(foundUser==null || foundUser.getStatus() == UserStatus.DELETED) throw new ApiRequestException("Kullanıcı Bulunamadı");
        return ResponseEntity.ok(userMapper.toUserDetailDTO(foundUser, currentUser));
    }


    public ResponseEntity<List<User>> getAll()
    {
        return ResponseEntity.ok(userRepo.findAll());
    }

    public ResponseEntity<User> getMe(){
        Optional<User> currentUser = userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        return ResponseEntity.ok(currentUser.get());
    }

    public ResponseEntity<BasicUserDto> followUser(Long userId){
        User currentUser =authService.getAuthenticatedUser();
        Optional<User> targetUser = userRepo.findById(userId);
        if(targetUser.isEmpty()){
            throw new ApiRequestException("Kullanıcı bulunamadı.");
        }
        currentUser.getFollowings().add(targetUser.get());
        targetUser.get().getFollowers().add(currentUser);
        userRepo.save(currentUser);
        userRepo.save(targetUser.get());
        return ResponseEntity.ok(userMapper.toBasicUserDTO(targetUser.get(), currentUser));
    }

    public ResponseEntity<BasicUserDto> unfollowUser(Long userId){
        User currentUser =authService.getAuthenticatedUser();
        Optional<User> targetUser = userRepo.findById(userId);

        if(targetUser.isEmpty()){
            throw new ApiRequestException("Kullanıcı bulunamadı.");
        }

        currentUser.getFollowings().remove(targetUser.get());
        targetUser.get().getFollowers().remove(currentUser);
        userRepo.save(currentUser);
        return ResponseEntity.ok(userMapper.toBasicUserDTO(targetUser.get(), currentUser));
    }
}
