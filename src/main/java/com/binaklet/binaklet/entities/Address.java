package com.binaklet.binaklet.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue
    Long id;

    String addressLine1;

    String addressLine2;

    String country;

    String city;

    String district;

    String postalCode;

    String contactName;

    String contactPhoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;
}
