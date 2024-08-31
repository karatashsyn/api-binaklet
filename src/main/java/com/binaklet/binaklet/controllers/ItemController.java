package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.dto.requests.item.MyItemsRequest;
import com.binaklet.binaklet.dto.requests.item.SearchItemRequest;
import com.binaklet.binaklet.dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.dto.responses.item.ItemDetailDTO;
import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.dto.requests.item.CreateItemRequest;
import com.binaklet.binaklet.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);


    @GetMapping
    public ResponseEntity<List<BasicItemDTO>> getItems(@Valid @ModelAttribute SearchItemRequest request)
    {
        return itemService.getAll(request);
    }


    @GetMapping("/me")
    public ResponseEntity<List<BasicItemDTO>> getMyItems(@Valid @ModelAttribute MyItemsRequest request )
    {
        return itemService.getMyItems(request);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Item> createItem(@Valid @ModelAttribute CreateItemRequest request) {
        return itemService.create(request);
    }


    @GetMapping({"/{itemId}"})
    public ResponseEntity<ItemDetailDTO> getItem(@PathVariable Long itemId){
            return itemService.getById(itemId);
    }


    @PostMapping({"/delete"})
    public ResponseEntity<ItemDetailDTO> deleteItem(Long itemId){
        return itemService.delete(itemId);
    }
    }
