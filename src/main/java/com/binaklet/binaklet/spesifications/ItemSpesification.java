package com.binaklet.binaklet.spesifications;

import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.Category;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.repositories.UserRepository;
import com.binaklet.binaklet.services.CategoryService;
import com.binaklet.binaklet.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemSpesification {

    private final CategoryService categoryService;
    private final UserService userService;
    private final UserRepository userRepository;
    public Specification<Item> status(ItemStatus status){
        return (root,query,cb)->
                cb.equal(root.get("status"),status);
    }

    public Specification<Item>nameLike(String searchKey){
        return (root,query,cb)->cb.like(root.get("name"), "%" + searchKey.toLowerCase()+"%");
    }

    public Specification<Item> maxPrice(int max){
        return (root,query,cb)->
            cb.lessThanOrEqualTo(root.get("price"),  max);
    }
    public Specification<Item> minPrice(int min){
        return (root,query,cb)->
                cb.greaterThanOrEqualTo(root.get("price"),  min);
    }
    public Specification<Item> byCategory(Long categoryId){
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            return (root,query,cb)->
                    cb.equal(root.get("category"),category);

        }
        else{
            return null;
        }

    }

    public Specification<Item> byUser(Long id){
        User filterUser = userRepository.getById(id);
        return (root, query, cb) ->cb.equal(root.get("user"),filterUser);
    }


    public Specification<Item> applyFilters(String searchKey, Integer maxPrice, Integer minPrice, Long userId, ItemStatus itemStatus, Long categoryId){
        Specification<Item> spec = null;
        if(searchKey!=null && !searchKey.isBlank()){
            spec=nameLike(searchKey);
        }
        else{
            spec = nameLike("");
        }
        if(userId!=null){
            spec = spec.and(byUser(userId));
        }

        if(maxPrice!=null && maxPrice>=0){
         spec=spec.and(maxPrice(maxPrice));
        }if( minPrice!=null && minPrice>=0){
         spec=spec.and(minPrice(minPrice));
        }
        if(itemStatus!=null){
            spec = spec.and(status(itemStatus));
        }
        if(categoryId!=null){
            spec=spec.and(byCategory(categoryId));
        }

        return spec;

    }


}
