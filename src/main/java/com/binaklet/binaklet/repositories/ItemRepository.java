package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
