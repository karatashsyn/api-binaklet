package com.binaklet.binaklet.entities;


import com.binaklet.binaklet.enums.PaymentMethod;
import com.binaklet.binaklet.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "transporter")
public class Transporter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String name;

//    @OneToMany(mappedBy = "transporter")
//    List<UserComment> comments;

    @NotNull
    Float volumeCapacity;

    @NotNull
    Float massCapacity;

    @NotNull
    Float occupiedCapacity;

    @Email
    @NotNull
    String email;

    @NotNull
    String phoneNumber;

    @NotNull
    String addressText;

    @NotNull
    String brandName;

    @OneToMany(mappedBy = "transporter",fetch = FetchType.EAGER)//mapped by string is = manyToOne variable name
    List<Order> orders;


}
