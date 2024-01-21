package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.ItemType;
import com.binaklet.binaklet.enums.ItemStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long>,  JpaSpecificationExecutor<Item> {


}
