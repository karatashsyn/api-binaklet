package com.binaklet.binaklet.entities;


import com.binaklet.binaklet.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
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

    @PrePersist
    protected void onCreate(){
        createdDate=new Date();
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    @CreatedDate
    Date createdDate;


    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "payment_id")
    Payment payment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pickup_address_id")
    @JsonIgnore
    Address pickUpAddress;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="delivery_address_id")
    @JsonIgnore
    Address deliverAddress;



    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name="item_id"))
    List<Item> items;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_user_id")
    User buyer;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_user_id")
    User seller;


    @ManyToOne
    @JoinColumn(name = "transporter_id")
    Transporter transporter;

}
