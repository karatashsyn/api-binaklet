package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
}
