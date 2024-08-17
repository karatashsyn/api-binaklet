package com.binaklet.binaklet.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "profile")
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String avatar;

    String description;

}
