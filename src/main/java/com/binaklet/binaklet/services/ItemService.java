package com.binaklet.binaklet.services;
import com.binaklet.binaklet.DTOs.BasicUserDto;
import com.binaklet.binaklet.DTOs.ItemDetailDto;
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
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        List<Item> allItems= itemRepository.findAll(itemSpec.applyFilters(searchKey,maxPrice,minPrice,userId, itemStatus,typeId));
        return allItems.stream().filter(item -> !item.getUser().getId().equals(currentUser.get().getId())).collect(Collectors.toList());
    }

    public List<Item> getMyItems(String searchKey, Integer maxPrice, Integer minPrice, ItemStatus itemStatus,Long typeId){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        return itemRepository.findAll(itemSpec.applyFilters(searchKey,maxPrice,minPrice,currentUser.get().getId(),itemStatus,typeId));
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
    public ItemDetailDto getById(Long id){
        Optional<Item> foundItem = itemRepository.findById(id);
        if(foundItem.isEmpty()){throw new ApiRequestException("Ürün bulunamadı.");}
        Item item = foundItem.get();
        User owner = item.getUser();
        BasicUserDto ownerDto = BasicUserDto.builder().email(owner.getEmail()).addresses(owner.getAddresses().stream().map(address -> address.getAddressText()).collect((Collectors.toList()))).firstName(owner.getFirstName()).lastName(owner.getLastName()).build();
        return ItemDetailDto.builder().name(item.getName()).id(item.getId()).mass(item.getMass()).brand(item.getBrand()
        ).age(item.getAge()).owner(ownerDto).price(item.getPrice()).status(item.getStatus()).description(item.getDescription()).images(item.getImages()).type(item.getItemType()).height(item.getHeight()).width(item.getWidth()).depth(item.getDepth()
        ).build();

    }

    public Item get(Long id){
        Optional<Item> foundItem = itemRepository.findById(id);
        if(foundItem.isEmpty()){throw new ApiRequestException("Item Bulunamadı");}
        return foundItem.get();
    }

    public void assignToOrder(Item itemToBeAssigned, Order order) {
        itemToBeAssigned.setOrder(order);
        itemToBeAssigned.setStatus(ItemStatus.SOLD);
        itemRepository.save(itemToBeAssigned);
    }



    public void delete(Long itemId) {
        Optional<Item> itemToBeDeleted = itemRepository.findById(itemId);
        if(itemToBeDeleted.isEmpty()){throw new ApiRequestException("Ürün bulunamadı");}
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        if(! itemToBeDeleted.get().getUser().equals(currentUser.get())){throw new ApiRequestException("Bu ürünü silemezsiniz");}
        itemRepository.delete(itemToBeDeleted.get());
    }
}
