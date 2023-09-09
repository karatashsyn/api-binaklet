package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Image;
import com.binaklet.binaklet.repositories.ImageRepository;
import com.google.cloud.storage.*;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends BaseService<Image,Long>{
    @Autowired
    ImageRepository imageRepository;
    public ImageService(JpaRepository<Image, Long> repository) {
        super(repository);
    }
    public Image storeImage (
//            String fileName, byte[] imageBytes
    ){
        Image tempImg = new Image();
        tempImg.setUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfz6zgzbGiNXaRHVj9TlGFsWYVxdWYvvyFvQ&usqp=CAU");
        tempImg.setFileName("testFile");
        return tempImg;


    }
}
