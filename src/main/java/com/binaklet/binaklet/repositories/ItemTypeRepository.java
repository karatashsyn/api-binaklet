package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<Category, Long> {
}
