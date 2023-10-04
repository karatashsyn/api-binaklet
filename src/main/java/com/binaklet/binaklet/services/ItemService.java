package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.repositories.ImageRepository;
import com.binaklet.binaklet.repositories.ItemRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.spesifications.ItemSpesification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService{
    private final ItemRepository itemRepository;
    private final ItemSpesification itemSpec;
    private  final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final FileService fileService;
//    public List<Item> getByItemType(ItemType itemType){
//        return  itemRepository.findByItemType(itemType);
//    }


    public List<Item> getAll(String searchKey, Integer maxPrice, Integer minPrice, Long userId, ItemStatus itemStatus,Long typeId){
        return itemRepository.findAll(itemSpec.applyFilters(searchKey,maxPrice,minPrice,userId,itemStatus,typeId));
    }

    public Item create (String name, ItemType itemType, String description, Integer price, Float height, Float width, MultipartFile[] images, Float mass, String brand) throws Exception{
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Item itemToCreate=  new Item();
        itemToCreate.setName(name);
        Optional<User> user = userRepository.findById(Long.parseLong("1"));
        itemToCreate.setUser(user.get());
        itemToCreate.setItemType(itemType);
        itemToCreate.setDescription(description);
        itemToCreate.setPrice(price);
        itemToCreate.setHeight(height);
        itemToCreate.setWidth(width);
        List<String> imageUrls = fileService.uploadFiles(images);
        List<Image> imagesToSave = new ArrayList<Image>();
        itemToCreate.setMass(mass);
        itemToCreate.setBrand(brand);
        for (String url:imageUrls) {
            Image newImg = new Image();
            newImg.setUrl(url);
            Image savedImage = imageRepository.save(newImg);
            imagesToSave.add(savedImage);
        }

        Item savedItem = itemRepository.save(itemToCreate);
        for (Image img:imagesToSave
             ) {
            img.setItem(savedItem);
            imageRepository.save(img);
        }
        savedItem.setImages(imagesToSave);
        return savedItem;
    }

    public Item getById(Long id){
        return itemRepository.findById(id).orElse(null);
    }

    public void delete(Item itemToBeDeleted) {
        itemRepository.delete(itemToBeDeleted);
    }
}
