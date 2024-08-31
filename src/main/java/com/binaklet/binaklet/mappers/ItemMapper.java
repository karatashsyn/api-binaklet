package com.binaklet.binaklet.mappers;

import com.binaklet.binaklet.dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.dto.responses.item.MyItemDTO;
import com.binaklet.binaklet.entities.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemMapper {

    public static BasicItemDTO toBasicItemDTO(Item item){
    BasicItemDTO basicItemDTO = new BasicItemDTO();
    basicItemDTO.setId(item.getId());
    basicItemDTO.setName(item.getName());
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

    public static MyItemDTO toMyItemDTO(Item item){
        MyItemDTO myItemDTO = new MyItemDTO();
        myItemDTO.setId(item.getId());
        myItemDTO.setName(item.getName());
        myItemDTO.setPrice(item.getPrice());
        myItemDTO.setWidth(item.getWidth());
        myItemDTO.setHeight(item.getHeight());
        myItemDTO.setDepth(item.getDepth());
        myItemDTO.setMass(item.getMass());
        myItemDTO.setBrand(item.getBrand());
        myItemDTO.setStatus(item.getStatus());
        myItemDTO.setDescription(item.getDescription());
        myItemDTO.setImages(item.getImages());
        myItemDTO.setType(item.getCategory());
        return myItemDTO;
    }

    public static List<BasicItemDTO> toBasicItemDTOList(List<Item> items){
        if(items==null || items.isEmpty()){return new ArrayList<>();}
        return items.stream().map(ItemMapper::toBasicItemDTO).toList();
    }

    public static List<MyItemDTO> toMyItemsDTOList(List<Item> items){
        if(items==null || items.isEmpty()){return new ArrayList<>();}
        return items.stream().map(ItemMapper::toMyItemDTO).toList();
    }

}
