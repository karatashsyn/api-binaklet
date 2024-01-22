package com.binaklet.binaklet.services.admin;


import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.repositories.ImageRepository;
import com.binaklet.binaklet.repositories.ItemRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.services.FileService;
import com.binaklet.binaklet.spesifications.ItemSpesification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminItemService {
    private final ItemRepository itemRepository;
    private final ItemSpesification itemSpec;
    private  final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final FileService fileService;

    public List<Item> getAll(String searchKey, Integer maxPrice, Integer minPrice, Long userId, ItemStatus itemStatus, Long typeId){
        return itemRepository.findAll(itemSpec.applyFilters(searchKey,maxPrice,minPrice,userId, itemStatus,typeId));
    }

    public void deleteItem(Long itemId){
        Optional<Item> foundItem = itemRepository.findById(itemId);
        if(foundItem.isEmpty()){throw new ApiRequestException("Silmeye çalıştığınız ürün sistemde bulunmamaktadır.");}
        itemRepository.deleteById(itemId);
    }
}
