package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
