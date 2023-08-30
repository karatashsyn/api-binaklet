package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service

public class ItemTypeService extends BaseService<ItemType,Long> {
    public ItemTypeService(JpaRepository<ItemType, Long> repository) {
        super(repository);
    }
}
