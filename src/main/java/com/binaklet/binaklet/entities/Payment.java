package com.binaklet.binaklet.entities;


import com.binaklet.binaklet.enums.PaymentMethod;
import com.binaklet.binaklet.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Float amount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @JsonIgnore
    @OneToOne(mappedBy = "payment")
    Order order;

    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    @PrePersist
    protected void onCreate(){
        createdDate=new Date();
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    @CreatedDate
    Date createdDate;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;









}



