package com.binaklet.binaklet.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.ImmutableList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {


    private static String[] types = {".jpeg", ".png", ".heic", "jpg", "webp"};

    private Storage storage;

    public List<String> uploadFiles(MultipartFile[] files) throws Exception{
        this.storage = StorageOptions.getDefaultInstance().getService();
        List<String> urls = new ArrayList<>();
        for (MultipartFile file: files
             ) {
            if(file.getOriginalFilename()==null){
                throw  new Exception("FileName cannot be empty");
            }
            //TODO: Checks for filetype,  ...




            BlobId blobId = BlobId.of("binaklet-item-pics",file.getOriginalFilename());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

            Blob blob = storage.create(blobInfo,file.getBytes());
            urls.add(blob.getMediaLink());

        }
        return ImmutableList.copyOf(urls);
    }

}
