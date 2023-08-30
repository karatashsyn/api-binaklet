package com.binaklet.binaklet.entities;


import com.binaklet.binaklet.enums.PaymentMethod;
import com.binaklet.binaklet.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Float amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    @CreatedDate
    OffsetDateTime createdDate;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;









}



