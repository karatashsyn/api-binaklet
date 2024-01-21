package com.binaklet.binaklet.entities;

import com.binaklet.binaklet.enums.ItemStatus;
import com.binaklet.binaklet.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;


    String name;



    Integer price;

    Float mass;


    String brand;

    Float age;

    ItemStatus status;

    @Lob
    @Column(columnDefinition = "text")
    String description;

    @OneToMany(mappedBy = "item",fetch = FetchType.EAGER) //DONE
    List<Image> images;


    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "order_id")
    Order order;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;


    //Fetch type eager so that app can suggest related products
    @ManyToMany(mappedBy = "items",fetch = FetchType.EAGER)
    List<Tag> tags;

    @ManyToOne(fetch = FetchType.EAGER) //DONE
    @JoinColumn(name = "item_type_id")
    ItemType itemType;

    Float height;

    Float width;

    Float depth;



    @PrePersist
    protected void onCreate(){
        createdDate=new Date();
        setStatus(ItemStatus.AVAILABLE);

    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    @CreatedDate
    Date createdDate;


}
