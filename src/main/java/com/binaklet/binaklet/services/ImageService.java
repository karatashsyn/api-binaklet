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
    public String storeImage (String fileName, byte[] imageBytes){
        System.out.println(fileName);
        System.out.println(imageBytes.toString());
        try {

            Storage storage = StorageOptions.getDefaultInstance().getService();
            String bucketName = "binaklet-item-pics";
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName,fileName).build();
            Blob blob = storage.create(blobInfo,imageBytes);
            return "https://storage.googleapis.com/"+bucketName+"/"+fileName;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }

    }
}
