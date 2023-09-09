package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.repositories.ItemRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.spesifications.ItemSpesification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService{
    private final ItemRepository itemRepository;
    private final ItemSpesification itemSpec;
    private  final UserRepository userRepository;
//    public List<Item> getByItemType(ItemType itemType){
//        return  itemRepository.findByItemType(itemType);
//    }


    public List<Item> getAll(String searchKey, Integer maxPrice, Integer minPrice, Long userId, ItemStatus itemStatus,ItemType type){
        return itemRepository.findAll(itemSpec.applyFilters(searchKey,maxPrice,minPrice,userId,itemStatus,type));
    }

    public Item create (String name, ItemType itemType, String description, Integer price, Float height, Float width, List<Image> images, Float mass,String brand){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Item itemToCreate=  new Item();
        itemToCreate.setName(name);
        itemToCreate.setUser(currentUser.get());
        itemToCreate.setItemType(itemType);
        itemToCreate.setDescription(description);
        itemToCreate.setPrice(price);
        itemToCreate.setHeight(height);
        itemToCreate.setWidth(width);
        itemToCreate.setImages(images);
        itemToCreate.setMass(mass);
        itemToCreate.setBrand(brand);
        return itemRepository.save(itemToCreate);
    }

}
