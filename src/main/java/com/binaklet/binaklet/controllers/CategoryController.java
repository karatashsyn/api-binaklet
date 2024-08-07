package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.dto.requests.item.SearchItemRequest;
import com.binaklet.binaklet.dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.entities.Category;
import com.binaklet.binaklet.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<List<Category>> getItems()
    {
        return  ResponseEntity.ok(categoryService.getAll());
    }

}
