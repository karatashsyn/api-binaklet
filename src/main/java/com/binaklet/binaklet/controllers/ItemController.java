package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.requests.ItemCreateRequest;
import com.binaklet.binaklet.services.*;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;
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
                               @RequestParam(value = "type",required = false) Long typeId )
    {
        return itemService.getAll(searchKey,maxPrice,minPrice,userId,status,typeId);

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Item> createitem(
            @RequestParam(value = "images",required = false)MultipartFile[] images,
            @RequestParam("name") String name,
            @RequestParam(value = "age", required = false) Float age,
            @RequestParam("mass") Float mass,
            @RequestParam("price") Integer price,
            @RequestParam(value = "brand", required = false ) String brand,
            @RequestParam("description") String description,
            @RequestParam(value = "tagIds", required = false) Long[] tagIds,
            @RequestParam("typeId") Long typeId,
            @RequestParam("height") Float height,
            @RequestParam("width") Float width
            ) throws IOException,Exception {
        List<Image> imgList = new ArrayList<>();
        Item itemToCreate = new Item();
        itemToCreate.setImages(imgList);
        ItemType itemType = itemTypeService.getById(typeId);
        if (itemType != null) {
            return ResponseEntity.ok().body(itemService.create(name, itemType, description, price, height, width, images, mass, brand));
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping({"/{itemId}"})

    public Item getitem(@PathVariable Long itemId){
        return itemService.getById(itemId);

    }

//    @DeleteMapping({"/{itemId}"})
//    public ResponseEntity<Item> deleteItem(@PathVariable Long itemId){
//        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
//        Item itemToBeDeleted = itemService.getById(itemId);
//        if(itemToBeDeleted==null){
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        else if(!itemToBeDeleted.getUser().getEmail().equals(currentUserName)){
//            return ResponseEntity.status(403).body(null);
//        }
//        else if(itemToBeDeleted!=null && itemToBeDeleted.getUser().getEmail().equals(currentUserName)){
//            itemService.delete(itemToBeDeleted);
//        }
//        return null;
//    }

//    @PostMapping({"/{itemId}"})
//    public Item updateitem(@PathVariable Long itemId, @RequestBody Item newItem){
//        return itemService.update(itemId,newItem);
//    }
}
