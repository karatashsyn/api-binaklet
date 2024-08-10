package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.dto.requests.item.CreateItemRequest;
import com.binaklet.binaklet.dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.dto.responses.item.ItemDetailDTO;
import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.services.FavouriteService;
import com.binaklet.binaklet.services.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favourites")
@Log4j2
@Validated
public class FavouritesController {
    private final ItemService itemService;
    private final FavouriteService favouriteService;
    @GetMapping({"/all"})
    public ResponseEntity<List<BasicItemDTO>> getFavs(){
        return favouriteService.getFavourites();
    }
    @PostMapping({"/add/{itemId}"})
    public ResponseEntity<ItemDetailDTO> addToFav(@PathVariable Long itemId){
        return favouriteService.addToFavourite(itemId);
    }

    @PostMapping({"/remove/{itemId}"})
    public ResponseEntity<ItemDetailDTO> removeFav(@PathVariable Long itemId){
        return favouriteService.removeFavourite(itemId);
    }



}
