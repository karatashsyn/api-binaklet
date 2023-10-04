package com.binaklet.binaklet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "userComment")
@Data
public class UserComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String comment;

    @ManyToOne()
    User user;

    @JsonIgnore
    @ManyToOne()
    Transporter transporter;
}
