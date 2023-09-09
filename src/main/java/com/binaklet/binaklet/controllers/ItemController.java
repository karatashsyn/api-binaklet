package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.requests.ItemCreateRequest;
import com.binaklet.binaklet.services.*;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private ItemService itemService;
    ImageService imageService;
    UserService userService;
    ItemTypeService itemTypeService;
    TagService tagService;

    public ItemController (ItemService itemService, ImageService imageService, UserService userService, ItemTypeService itemTypeService, TagService tagService){
        this.itemService=itemService;
        this.imageService=imageService;
        this.userService=userService;
        this.itemTypeService=itemTypeService;
        this.tagService=tagService;
    }

    @GetMapping
    public List<Item> getItems(@RequestParam(value = "searchKey",required = false) String searchKey,
                               @RequestParam(value = "max",required = false) Integer maxPrice,
                               @RequestParam(value = "min",required = false) Integer minPrice,
                               @RequestParam(value = "byUser",required = false) Long userId ,
                               @RequestParam(value = "status",required = false) ItemStatus status ,
                               @RequestParam(value = "type",required = false) ItemType type )
    {
        return itemService.getAll(searchKey,maxPrice,minPrice,userId,status,type);

    }

    @PostMapping(consumes = "multipart/form-data")
    public Item createitem(
//            @RequestParam(value = "file",required = false)MultipartFile file,
            @RequestParam("name") String name,
//            @RequestParam("img") String img,
            @RequestParam(value = "age",required = false) Float age,
            @RequestParam("mass") Float mass,
            @RequestParam("price") Integer price,
            @RequestParam(value = "brand",required = false ) String brand,
            @RequestParam("description") String description,
            @RequestParam(value = "tagIds",required = false) Long[] tagIds,
            @RequestParam("itemTypeId") Long itemTypeId,
            @RequestParam("height") Float height,
            @RequestParam("width") Float width
            ) throws IOException {
        List<Image> imgList = new ArrayList<>();
        imgList.add(imageService.storeImage());
        Item itemToCreate = new Item();
        itemToCreate.setImages(imgList);
        ItemType itemType = itemTypeService.getById(itemTypeId);

        return itemService.create(name,itemType,description,price,height,width,imgList,mass,brand);

//        Item itemToSave = new Item();
//
//        User user = userService.getById(userId);
//        ItemType typeOfItem = itemTypeService.getById(itemTypeId);
//
//        ///////////////
//        Storage storage = StorageOptions.getDefaultInstance().getService();
//
//        String bucketName = "binaklet-item-pics"; // Replace with your GCS bucket name
//        String fileName = file.getOriginalFilename(); // Use the original filename or generate a unique one
//
//        Bucket.BlobTargetOption option = null;
//        // Upload the file to Google Cloud Storage
//        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName).build();
//        Blob blob = storage.create(blobInfo, file.getBytes());
//
//        // Get the URL of the uploaded image
//        String imageUrl = blob.getMediaLink();
//
//        System.out.println("Image uploaded successfully. URL: " + imageUrl);
//        ///////////////////
//
//
//        if(user!=null && typeOfItem!=null){
//
//
//            itemToSave.setUser(user);
//            itemToSave.setPrice(price);
//            itemToSave.setItemType(typeOfItem);
//            itemToSave.setMass(mass);
//            itemToSave.setHeight(height);
//            itemToSave.setWidth(width);
//            itemToSave.setName(name);
//            itemToSave.setMass(mass);
//            itemToSave.setAge(age);
//            itemToSave.setDescription(description);
//            Item createdItem =  itemService.create(itemToSave);
//            Image newImage = new Image();
//            newImage.setUrl(img);
//            newImage.setFileName("Image_test");
//            newImage.setItem(createdItem);
//            Image createdImage = imageService.create(newImage);
//            return createdItem;
//        }
//
//        else{
//            return null;
//        }
//
//
    }

//    @GetMapping({"/{itemId}"})

//    public Item getitem(@PathVariable Long itemId){
//        return itemService.getById(itemId);

//    }

//    @PostMapping({"/{itemId}"})
//    public Item updateitem(@PathVariable Long itemId, @RequestBody Item newItem){
//        return itemService.update(itemId,newItem);
//    }
}
