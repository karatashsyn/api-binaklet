package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends BaseService<Image,Long>{
    public ImageService(JpaRepository<Image, Long> repository) {
        super(repository);
    }
}
