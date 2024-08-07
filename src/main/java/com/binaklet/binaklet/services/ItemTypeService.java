package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service

public class ItemTypeService extends BaseService<Category,Long> {
    public ItemTypeService(JpaRepository<Category, Long> repository) {
        super(repository);
    }
}
