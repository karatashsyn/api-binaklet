package com.binaklet.binaklet.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "item_type")
public class ItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    String name;

    @OneToMany(mappedBy = "itemType",fetch = FetchType.LAZY)
    List<Item> items;


}
