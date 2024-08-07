package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Transporter;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.entities.UserComment;
import com.binaklet.binaklet.repositories.TransporterRepository;
import com.binaklet.binaklet.repositories.UserCommentRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import dto.requests.transporter.UserCommentCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCommentService {
    private final UserCommentRepository userCommentRepo;
    private final UserRepository userRepository;
    private final TransporterRepository transporterRepository;
    public UserComment create (UserCommentCreateRequest commentRequest){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        UserComment commentToCreate = new UserComment();
        Transporter transporterOfTheComment = transporterRepository.findById(commentRequest.getTransporterId()).orElse(null);
        if(currentUser!=null && currentUser.isPresent() ){
            commentToCreate.setUser(currentUser.get());
        }
        if(transporterOfTheComment!=null){
            commentToCreate.setTransporter(transporterOfTheComment);
        }
        //TODO else return bad request

        commentToCreate.setComment(commentRequest.getComment());
        return userCommentRepo.save(commentToCreate);
    }

    public List<UserComment> getAll(){
        return userCommentRepo.findAll();
    }


}
