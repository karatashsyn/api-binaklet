package com.binaklet.binaklet.services;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import org.springframework.stereotype.Service;
import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.repositories.ImageRepository;
import com.binaklet.binaklet.repositories.ItemRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.spesifications.ItemSpesification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ItemService{
    private final ItemRepository itemRepository;
    private final ItemSpesification itemSpec;
    private  final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final FileService fileService;

//    private boolean isIdValid(String id){
//        return
//    }


    public List<Item> getAll(String searchKey, Integer maxPrice, Integer minPrice, Long userId, ItemStatus itemStatus,Long typeId){
        return itemRepository.findAll(itemSpec.applyFilters(searchKey,maxPrice,minPrice,userId,itemStatus,typeId));
    }


    public List<Item> getAllItemsExceptMine(String searchKey, Integer maxPrice, Integer minPrice, Long userId, ItemStatus itemStatus,Long typeId){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        return itemRepository.findAll(itemSpec.applyFilters(searchKey,maxPrice,minPrice,userId,itemStatus,typeId)).stream().filter(item-> item.getUser().getId().equals(currentUser.get().getId())).collect(Collectors.toList());
    }




    public Item create (String name, ItemType itemType, String description, Integer price, Float height, Float width, Float depth, MultipartFile[] images, Float mass, String brand) throws Exception{
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        Item itemToCreate=  new Item();
        itemToCreate.setName(name);
        itemToCreate.setUser(currentUser.get());
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
        Optional<Item> foundItem = itemRepository.findById(id);
        if(foundItem.isEmpty()){throw new ApiRequestException("Ürün bulunamadı.");}
        return foundItem.get();
    }

    public void assignToOrder(Item itemToBeAssigned, Order order) {
        itemToBeAssigned.setOrder(order);
        itemToBeAssigned.setStatus(ItemStatus.SOLD);
        itemRepository.save(itemToBeAssigned);
    }


    //TODO:Item kullanıcıya mı ait kontrolü
    public void delete(Item itemToBeDeleted) {
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        if(! itemToBeDeleted.getUser().equals(currentUser.get())){throw new ApiRequestException("Bu ürünü silemezsiniz");}
        itemRepository.delete(itemToBeDeleted);
    }
}
