package com.binaklet.binaklet.spesifications;

import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.ItemType;
import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.services.ItemTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class ItemSpesification {

    private final ItemTypeService itemTypeService;
    public Specification<Item> status(ItemStatus status){
        return (root,query,cb)->
                cb.equal(root.get("status"),status);
    }

    public Specification<Item>nameLike(String searchKey){
        return (root,query,cb)->cb.like(root.get("name"), "%"+searchKey.toLowerCase()+"%");
    }

    public Specification<Item> maxPrice(int max){
        return (root,query,cb)->
            cb.lessThanOrEqualTo(root.get("price"),  max);
    }
    public Specification<Item> minPrice(int min){
        return (root,query,cb)->
                cb.greaterThanOrEqualTo(root.get("price"),  min);
    }
    public Specification<Item> byType(Long typeId){
        ItemType itemType = itemTypeService.getById(typeId);
        if(itemType!=null){
            return (root,query,cb)->
                    cb.equal(root.get("itemType"),itemType);

        }
        else{
            return null;
        }

    }

    public Specification<Item> byUser(Long id){
        return (root, query, cb) ->cb.equal(root.get("id"),id);
    }


    public Specification<Item> applyFilters(String searchKey, Integer maxPrice, Integer minPrice, Long userId, ItemStatus itemStatus, Long itemTypeId){
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
        if(itemTypeId!=null){
            spec=spec.and(byType(itemTypeId));
        }

        return spec;

    }


}
