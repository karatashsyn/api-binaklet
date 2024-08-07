package com.binaklet.binaklet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    String name;


    @JsonIgnore
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    List<Item> items;

}
