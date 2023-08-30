package com.binaklet.binaklet.entities;


import com.binaklet.binaklet.enums.OrderStatus;
import com.binaklet.binaklet.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;
import java.util.List;


@Data
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;


    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @CreatedDate
    OffsetDateTime createdDate;


    @OneToOne()
    @JoinColumn(name = "payment_id")
    Payment payment;

    @ManyToOne
    @JoinColumn(name = "pickup_adress_id")
    Address pickUpAddress;

    @ManyToOne
    @JoinColumn
    Address deliverAddress;


    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY)
    List<Item> items;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_user_id")
    User buyer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_user_id")
    User seller;



}
