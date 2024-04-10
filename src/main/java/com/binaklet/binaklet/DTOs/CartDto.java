package com.binaklet.binaklet.DTOs;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartDto {
    Long id;
    List<ItemDetailDto> items;
}
