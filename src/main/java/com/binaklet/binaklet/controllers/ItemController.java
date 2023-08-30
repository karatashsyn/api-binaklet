package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.services.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {
    private ItemService itemService;

    public ItemController (ItemService itemService){
        this.itemService=itemService;
    }

    @GetMapping
    public List<Item> getItems(){
        return itemService.getAll();
    }

    @PostMapping()
    public Item createitem(@RequestBody Item newItem, HttpServletRequest request, @RequestBody String body){
        return itemService.create(newItem);
    }

    @GetMapping({"/{itemId}"})
    public Item getitem(@PathVariable Long itemId){
        return itemService.getById(itemId);
    }

    @PostMapping({"/{itemId}"})
    public Item updateitem(@PathVariable Long itemId, @RequestBody Item newItem){
        return itemService.update(itemId,newItem);
    }
}
