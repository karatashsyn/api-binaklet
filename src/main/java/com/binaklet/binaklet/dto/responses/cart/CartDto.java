package com.binaklet.binaklet.dto.responses.cart;

import com.binaklet.binaklet.dto.responses.item.ItemDetailDTO;
import com.binaklet.binaklet.dto.responses.user.BasicUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor(staticName = "build")
public class CartDto {
    Long id;

    // Seller id and his items
    Map<Long, List<ItemDetailDTO>> items;

    Integer total_items;

}
