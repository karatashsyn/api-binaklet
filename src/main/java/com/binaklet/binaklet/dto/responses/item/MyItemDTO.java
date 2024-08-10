package com.binaklet.binaklet.dto.responses.item;

import com.binaklet.binaklet.dto.responses.user.BasicUserDto;
import com.binaklet.binaklet.entities.Image;
import com.binaklet.binaklet.entities.Category;
import com.binaklet.binaklet.enums.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
@Builder
public class MyItemDTO {
    Long id;
    String name;
    Float price;
    Float width;
    Float height;
    Float depth;
    Float mass;
    String brand;
    ItemStatus status;
    String description;
    List<Image> images;
    Category type;
}
