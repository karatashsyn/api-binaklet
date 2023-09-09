package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {


    List<Item> findByItemType(ItemType itemType);

    @Query("SELECT i FROM Item i WHERE lower(i.name)  like concat('%', lower(:name) ,'%') ")
    List<Item> findByName(String name);
}
