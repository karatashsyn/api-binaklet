package com.binaklet.binaklet.dto.requests.item;

import com.binaklet.binaklet.enums.ItemStatus;
import lombok.Data;

@Data
public class MyItemsRequest {

    private String searchKey;

    private Integer maxPrice;

    private Integer minPrice;

    private ItemStatus status;

    private Long categoryId;

}