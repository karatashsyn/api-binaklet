package com.binaklet.binaklet.entities;

import com.binaklet.binaklet.enums.ItemCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "item_type")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    String name;

    ItemCategory value;

    @JsonIgnore
    @OneToMany(mappedBy = "itemType",fetch = FetchType.LAZY)
    List<Item> items;

}
