package com.binaklet.binaklet.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "image")
@Entity
public class Image {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne() //DONE
    @JoinColumn(name="item_id")
    Item item;

    String url;
}
