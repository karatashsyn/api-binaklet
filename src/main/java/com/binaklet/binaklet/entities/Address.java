package com.binaklet.binaklet.entities;


import com.binaklet.binaklet.enums.AddressStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String addressTitle;

    @NotNull
    String addressText;

    @NotNull
    String contactPhone;


    Boolean isUserDefault = false;

    AddressStatus status = AddressStatus.PRESENT;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;
}
