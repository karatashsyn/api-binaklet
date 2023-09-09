package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.ItemType;
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
    public List<ItemType> getItemsTypes(){
        return itemTypeService.getAll();
    }

    @PostMapping()
    public ItemType createItemType(@RequestBody ItemType newItem){
        return itemTypeService.create(newItem);
    }

    @GetMapping({"/{itemTypeId}"})
    public ItemType getItemType(@PathVariable Long itemTypeId){
        return itemTypeService.getById(itemTypeId);
    }

    @PostMapping({"/{itemTypeId}"})
    public ItemType updateItem(@PathVariable Long itemTypeId, @RequestBody ItemType newItemType){
        return itemTypeService.update(itemTypeId, newItemType);
    }
}
