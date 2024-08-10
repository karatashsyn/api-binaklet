package com.binaklet.binaklet.services;

import com.binaklet.binaklet.dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.dto.responses.item.ItemDetailDTO;
import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.mappers.ItemMapper;
import com.binaklet.binaklet.repositories.ItemRepository;
import com.binaklet.binaklet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavouriteService {
    private final UserRepository userRepository;
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    public ResponseEntity<List<BasicItemDTO>> getFavourites(){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}
        List<Item> userFavs =  currentUser.get().getFavourites();
        return ResponseEntity.ok(ItemMapper.toBasicItemDTOList(userFavs));
    };

    public ResponseEntity<ItemDetailDTO> addToFavourite(Long itemId){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}

        List<Item> userFavs =  currentUser.get().getFavourites();
        if(userFavs.stream().map(Item::getId).toList().contains(itemId)){
            throw new ApiRequestException("Bu ürün zaten favorilerde.");
        }
        Optional<Item> targetItem = itemRepository.findById(itemId);
        if(targetItem.isEmpty()){throw new ApiRequestException("Böyle bir ürün yok");}
        else{
            userFavs.add(targetItem.get());
        }
        currentUser.get().setFavourites(userFavs);
        userRepository.save(currentUser.get());
        return itemService.getById(itemId);

    };

    public ResponseEntity<ItemDetailDTO> removeFavourite(Long itemId){
        Optional<User> currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if(currentUser.isEmpty()){throw new ApiRequestException("Yetkili kullanıcı bulunamadı");}

        List<Item> userFavs =  currentUser.get().getFavourites();
        if(!userFavs.stream().map(Item::getId).toList().contains(itemId)){
            throw new ApiRequestException("Bu ürün zaten favorilerde değil.");
        }
        Optional<Item> targetItem = itemRepository.findById(itemId);
        if(targetItem.isEmpty()){throw new ApiRequestException("Böyle bir ürün yok");}
        else{
            userFavs.remove(targetItem.get());
        }
        currentUser.get().setFavourites(userFavs);
        userRepository.save(currentUser.get());
        return itemService.getById(itemId);

    };



}
