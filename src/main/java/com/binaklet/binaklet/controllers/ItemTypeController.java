package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.Category;
import com.binaklet.binaklet.services.ItemTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itemTypes")
public class ItemTypeController {
    private ItemTypeService itemTypeService;

    public ItemTypeController (ItemTypeService itemTypeService){
        this.itemTypeService=itemTypeService;
    }

    @GetMapping
    public List<Category> getItemsTypes(){
        return itemTypeService.getAll();
    }

    @PostMapping()
    public Category createItemType(@RequestBody Category newItem){
        return itemTypeService.create(newItem);
    }

    @GetMapping({"/{itemTypeId}"})
    public Category getItemType(@PathVariable Long itemTypeId){
        return itemTypeService.getById(itemTypeId);
    }

    @PostMapping({"/{itemTypeId}"})
    public Category updateItem(@PathVariable Long itemTypeId, @RequestBody Category newItemType){
        return itemTypeService.update(itemTypeId, newItemType);
    }
}
