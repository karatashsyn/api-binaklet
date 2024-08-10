package com.binaklet.binaklet.mappers;

import com.binaklet.binaklet.dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.entities.Item;

import java.util.List;

public class ItemMapper {

    public static BasicItemDTO toBasicItemDTO(Item item){

    BasicItemDTO basicItemDTO = new BasicItemDTO();
    basicItemDTO.setId(item.getId());
    basicItemDTO.setStatus(item.getStatus());
    basicItemDTO.setBrand(item.getBrand());
    basicItemDTO.setPrice(item.getPrice());
    if(!item.getImages().isEmpty()){
        String banner = item.getImages().get(0).getUrl();
        basicItemDTO.setBanner(banner);
    }
    else{
        basicItemDTO.setBanner("");
    }
    return basicItemDTO;
    }

    public static List<BasicItemDTO> toBasicItemDTOList(List<Item> items){
        return items.stream().map(ItemMapper::toBasicItemDTO).toList();
    }

}
