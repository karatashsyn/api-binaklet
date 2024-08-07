package com.binaklet.binaklet.dto.requests.item;

import com.binaklet.binaklet.enums.ItemStatus;
import lombok.Data;

@Data
public class SearchItemRequest {

    private String searchKey;

    private Integer maxPrice;

    private Integer minPrice;

    private Long byUserId;

    private ItemStatus status;

    private Long categoryId;

}