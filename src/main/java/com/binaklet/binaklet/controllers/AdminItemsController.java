package com.binaklet.binaklet.controllers;


import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.services.admin.AdminItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/items")
public class AdminItemsController {
    private final AdminItemService adminItemService;


    @GetMapping
    public List<Item> getAllItems(@RequestParam(value = "searchKey", required = false) String searchKey,
                                  @RequestParam(value = "max", required = false) Integer maxPrice,
                                  @RequestParam(value = "min", required = false) Integer minPrice,
                                  @RequestParam(value = "byUser", required = false) Long userId,
                                  @RequestParam(value = "status", required = false) ItemStatus status,
                                  @RequestParam(value = "type", required = false) Long typeId) {
        return adminItemService.getAll(searchKey, maxPrice, minPrice, userId, status, typeId);
    }

    @PostMapping("/{itemId}")
    public void deleteItem(@PathVariable String itemId) {
        try {
            Long id = Long.parseLong(itemId);
            adminItemService.deleteItem(id);
        } catch (NumberFormatException e) {
            throw new ApiRequestException("Lütfen geçerli bir ürün kimliği giriniz");
        } catch (Exception e) {

            //            throw e;
        }
    }

}
