package com.binaklet.binaklet.spesifications;

import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.ItemType;
import com.binaklet.binaklet.enums.ItemStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class ItemSpesification {

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

    public Specification<Item> byUser(Long id){
        return (root, query, cb) ->cb.equal(root.get("id"),id);
    }

    public Specification<Item> byType (ItemType type){
        return (root, query, cb) -> cb.equal(root.get("itemType"),type);
    }


    public Specification<Item> applyFilters(String searchKey, Integer maxPrice, Integer minPrice, Long userId, ItemStatus itemStatus, ItemType itemType){
        System.out.println("search is "+searchKey);
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
        if(itemType!=null){

        }

        return spec;

    }


}
