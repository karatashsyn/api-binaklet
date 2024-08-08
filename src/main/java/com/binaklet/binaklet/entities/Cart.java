package com.binaklet.binaklet.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "cart")
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;



    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinTable(name = "cart_item",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name="item_id"))
    List<Item> items;

    @PrePersist
    protected void onCreate()
    {
        this.items= new ArrayList<>();
    }
}
