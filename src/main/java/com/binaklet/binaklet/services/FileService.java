package com.binaklet.binaklet.services;

import com.google.cloud.storage.*;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileService {


    // private static String[] types = {".jpeg", ".png", ".heic", "jpg", "webp"};

    private Storage storage;
    private static final String STORAGE_BASE_URL="https://storage.googleapis.com/binaklet-bucket-april/";

    public List<String> uploadFiles(MultipartFile[] files) throws Exception{
        this.storage = StorageOptions.getDefaultInstance().getService();
        List<String> urls = new ArrayList<>();
        if(files==null || files.length==0 ) return ImmutableList.copyOf(urls);
        for (MultipartFile file: files
             ) {
            if(file.getOriginalFilename()==null){
                throw  new Exception("FileName cannot be empty");
            }
            //TODO: Checks for filetype,  ...


            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String uniqueName = java.util.UUID.randomUUID().toString()+ file.getOriginalFilename()+String.valueOf(System.currentTimeMillis());
            byte[] hashBytes = digest.digest(uniqueName.getBytes(StandardCharsets.UTF_8));
            String uuid = Base64.getEncoder().encodeToString(hashBytes).replaceAll("/","");
            BlobId blobId = BlobId.of("binaklet-bucket-april",uuid);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            Blob blob = storage.create(blobInfo,file.getBytes());
            urls.add(STORAGE_BASE_URL + uuid);

        }
        return ImmutableList.copyOf(urls);
    }

}
