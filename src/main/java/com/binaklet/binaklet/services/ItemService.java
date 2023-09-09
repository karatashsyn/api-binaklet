package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.ItemType;
import com.binaklet.binaklet.repositories.ItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService extends BaseService<Item,Long>{
    ItemRepository repo;
    public ItemService(JpaRepository<Item, Long> repository,ItemRepository itemRepo) {
        super(repository);
        this.repo=itemRepo;
    }

    public List<Item> getByItemType(ItemType itemType){
        return  repo.findByItemType(itemType);
    }

    public List<Item> getByName(String name){
        return repo.findByName(name);
    }
}
