package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService extends BaseService<Item,Long>{
    public ItemService(JpaRepository<Item, Long> repository) {
        super(repository);
    }
}
