package com.binaklet.binaklet.DTOs;

import dto.responses.item.ItemDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor(staticName = "build")
public class CartDto {
    Long id;
    List<ItemDetailDTO> items;
}
