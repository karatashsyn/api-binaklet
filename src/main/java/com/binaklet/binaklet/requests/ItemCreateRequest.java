package com.binaklet.binaklet.requests;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class ItemCreateRequest {

        Long userId;

        String name;

        Float price;

        Float mass;

        String brand;

        Float age;

        String description;

        Long[] tagIds;

        Long itemTypeId;

        Float height;

        Float width;




}
