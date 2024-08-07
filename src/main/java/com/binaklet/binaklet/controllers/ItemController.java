package com.binaklet.binaklet.controllers;

import dto.responses.item.ItemDetailDTO;
import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import dto.requests.item.CreateItemRequest;
import com.binaklet.binaklet.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
@Log4j2
@Validated
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
        System.out.println("SEARCH ITEMS runned");
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
            @Valid @RequestBody CreateItemRequest request
            ) throws IOException,Exception {

        return ResponseEntity.ok().body(itemService.create(request));


    }


    @GetMapping({"/{itemId}"})

    public ItemDetailDTO getItem(@PathVariable String itemId){
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
            ItemDetailDTO itemToBeDeleted = itemService.getById(id);
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
