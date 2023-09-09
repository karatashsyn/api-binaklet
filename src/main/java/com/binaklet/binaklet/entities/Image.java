package com.binaklet.binaklet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "image")
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fileName;


    @ManyToOne(fetch = FetchType.LAZY) //DONE
    @JoinColumn(name="item_id")
    @JsonIgnore
    Item item;


    String url;
}
