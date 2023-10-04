package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommentRepository extends JpaRepository<UserComment,Long> {
}
