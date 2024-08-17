package com.binaklet.binaklet.services;
import com.binaklet.binaklet.dto.requests.item.MyItemsRequest;
import com.binaklet.binaklet.dto.requests.item.SearchItemRequest;
import com.binaklet.binaklet.dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.dto.responses.user.BasicUserDto;
import com.binaklet.binaklet.dto.responses.item.ItemDetailDTO;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.dto.requests.item.CreateItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.binaklet.binaklet.entities.*;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.repositories.ImageRepository;
import com.binaklet.binaklet.repositories.ItemRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.spesifications.ItemSpesification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final CategoryService categoryService;



    public ResponseEntity<List<BasicItemDTO>> getAll(SearchItemRequest request){
        //TODO: replace "" with item.getImages().get(0).getUrl()
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        List<Item> allItems= itemRepository.findAll(itemSpec.applyFilters(request.getSearchKey(), request.getMaxPrice(),request.getMinPrice(),request.getByUserId(),request.getStatus(), request.getCategoryId()));
        // TODO: Replace banner with item.getImages.get(0).getUrl()
        List<BasicItemDTO> allItemsDTOs = allItems.stream()
            .filter(item -> !item.getUser().equals(currentUser.get()))
            .map(item -> BasicItemDTO.build(item.getId(), item.getStatus(), item.getBrand(), item.getName(), item.getPrice(), ""))
            .collect(Collectors.toList());

        return ResponseEntity.ok(allItemsDTOs);
    }

    //TODO: replace "" with item.getImages().get(0).getUrl()
    public ResponseEntity<List<BasicItemDTO>> getMyItems(MyItemsRequest request){

        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        List<Item> userItems = itemRepository.findAll(itemSpec.applyFilters(request.getSearchKey(), request.getMaxPrice(), request.getMinPrice(), currentUser.get().getId(), request.getStatus(), request.getCategoryId()));
        List<BasicItemDTO> userItemDTOs = userItems.stream().map(item->BasicItemDTO.build(item.getId(),item.getStatus(),item.getBrand(),item.getName(),item.getPrice(),"")).toList();

        return ResponseEntity.ok(userItemDTOs);
    }



    public ResponseEntity<Item> create (@ModelAttribute CreateItemRequest payload){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}


        String name = payload.getName();
        Long categoryId = payload.getCategoryId();

        String description = payload.getDescription();
        Float price = payload.getPrice();
        Float width = payload.getWidth();
        Float height = payload.getHeight();
        Float depth = payload.getDepth();
        Float mass = payload.getMass();
        MultipartFile[] images = payload.getImages();
        String brand = payload.getBrand();

        // TODO: Category should exist validation either apply here or with validation annotations.
        Category category = categoryService.getById(categoryId);
        if( category==null){
            throw new ApiRequestException("Böyle bir kategori bulunmamaktadir.");
        }



        Item itemToCreate=  new Item();
        itemToCreate.setName(name);
        itemToCreate.setUser(currentUser.get());
        itemToCreate.setCategory(category);
        itemToCreate.setDescription(description);
        itemToCreate.setPrice(price);
        itemToCreate.setDepth(depth);
        itemToCreate.setHeight(height);
        itemToCreate.setWidth(width);

        //TODO: Activate next line
      //List<String> imageUrls = fileService.uploadFiles(images);

        //TODO: Deactivate next line
        List<String> imageUrls =new ArrayList<>();

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

        return ResponseEntity.ok(savedItem);
    }
    public ResponseEntity<ItemDetailDTO> getById(Long id){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}

        Optional<Item> foundItem = itemRepository.findById(id);
        if(foundItem.isEmpty()){throw new ApiRequestException("Ürün bulunamadı.");}
        Item item = foundItem.get();
        User owner = item.getUser();
        BasicUserDto ownerDto = BasicUserDto.build(owner.getId(),owner.getEmail(),owner.getProfile().getName(),owner.getProfile().getAvatar(),owner.getRating(),owner.getRateCount(),owner.getAddresses().stream().map(Address:: getAddressText).toList());

        boolean isUserFavourite = currentUser.get().getFavourites().contains(item);
        ItemDetailDTO itemDetail = ItemDetailDTO.build(item.getId(),item.getName(),item.getPrice(),item.getWidth(),item.getHeight(),item.getDepth(),item.getMass(),item.getBrand(),item.getStatus(),item.getDescription(),item.getImages(),item.getCategory(),ownerDto,isUserFavourite);

        return ResponseEntity.ok(itemDetail);

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


    public ResponseEntity<ItemDetailDTO> delete(Long itemId) {
        Optional<Item> itemToBeDeleted = itemRepository.findById(itemId);
        if(itemToBeDeleted.isEmpty()){throw new ApiRequestException("Ürün bulunamadı");}
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        if(! itemToBeDeleted.get().getUser().equals(currentUser.get())){throw new ApiRequestException("Bu ürünü silemezsiniz");}
        itemRepository.delete(itemToBeDeleted.get());

        return ResponseEntity.ok(null);
    }


}
