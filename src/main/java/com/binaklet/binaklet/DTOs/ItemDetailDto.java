package com.binaklet.binaklet.DTOs;

import com.binaklet.binaklet.entities.Image;
import com.binaklet.binaklet.entities.ItemType;
import com.binaklet.binaklet.entities.Tag;
import com.binaklet.binaklet.enums.ItemStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ItemDetailDto {

    Long id;
    String name;
    Integer price;
    Float mass;
    String brand;
    Float age;
    ItemStatus status;
    String description;
    List<Image> images;
    ItemType type;
    Float height;
    Float width;
    Float depth;
    BasicUserDto owner;

}
