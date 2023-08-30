package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
