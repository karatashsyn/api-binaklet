package com.binaklet.binaklet.entities;

import com.binaklet.binaklet.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue
    Long id;

    @Enumerated(EnumType.STRING)
    List<UserRole> roles;

    @Enumerated(EnumType.STRING)
    UserRole currentRole;

    String firstName;

    String lastName;

    String email;

    String phoneNumber;

    @OneToMany(mappedBy = "user")//mapped by string is = manyToOne variable name
    List<Address> addresses;

    @CreatedDate
    OffsetDateTime createdDate;
}
