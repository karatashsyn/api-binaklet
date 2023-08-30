package com.binaklet.binaklet.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tag_item",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name="item_id"))
    List<Item> items;


}