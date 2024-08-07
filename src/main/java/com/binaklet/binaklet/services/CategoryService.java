package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service

public class CategoryService extends BaseService<Category,Long> {
    public CategoryService(JpaRepository<Category, Long> repository) {
        super(repository);
    }
}
