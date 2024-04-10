package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.DTOs.ItemDetailDto;
import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
@Log4j2
public class ItemController {
    private final ItemService itemService;
    private final ItemTypeService itemTypeService;
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
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


    //TODO: SecurityFilterde itemsin hepsine izin verme, getMyItemsi falan sil
    @GetMapping("/me")
    public List<Item> getMyItems(@RequestParam(value = "searchKey",required = false) String searchKey,
                               @RequestParam(value = "max",required = false) Integer maxPrice,
                               @RequestParam(value = "min",required = false) Integer minPrice,
                               @RequestParam(value = "status",required = false) ItemStatus status ,
                               @RequestParam(value = "type",required = false) Long typeId )
    {
        logger.info("GET MY ITEMS");
        return itemService.getMyItems(searchKey,maxPrice,minPrice,status,typeId);

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Item> createItem(
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
            @RequestParam("width") Float width,
            @RequestParam("depth") Float depth
            ) throws IOException,Exception {
        logger.info("CREATE ITEM");
        logger.info("CREATE ITEM/ NAME: " + name);
        logger.info("CREATE ITEM / IMAGES: " + images);
//        MultipartFile[] fakeImgList = new MultipartFile[0];

        ItemType itemType = itemTypeService.getById(typeId);
        if (itemType != null) {
            return ResponseEntity.ok().body(itemService.create(name, itemType, description, price, height, width,depth, images ,mass, brand));
        }
        else{
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping({"/{itemId}"})

    public ItemDetailDto getItem(@PathVariable String itemId){
        try {
            Long id = Long.parseLong(itemId.trim());
            return itemService.getById(id);
        }
        catch(NumberFormatException  e) {
            throw new ApiRequestException("Lütfen geçerli bir ürün kimliği giriniz");
        }
        catch (Exception e){
            throw e;
        }
    }


    @PostMapping({"/deleteItem"})
    public void deleteItem(Long itemId){
        try {
            Long id = Long.parseLong(itemId.toString().trim());
            ItemDetailDto itemToBeDeleted = itemService.getById(id);
            if(itemToBeDeleted==null){
                throw new ApiRequestException("Ürün bulunamadı.");
            }
            else{itemService.delete(id);}
        }
        catch(NumberFormatException  e) {
            throw new ApiRequestException("Lütfen geçerli bir ürün kimliği giriniz");
        }
        catch (Exception e){
            throw e;
        }
    }

//    @PostMapping({"/{itemId}"})
//    public Item updateitem(@PathVariable Long itemId, @RequestBody Item newItem){
//        return itemService.update(itemId,newItem);
//    }
}
