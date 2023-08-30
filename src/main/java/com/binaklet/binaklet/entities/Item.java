package com.binaklet.binaklet.entities;

import com.binaklet.binaklet.enums.ItemStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    String name;


    Float price;

    String brand;

    Float age;

    ItemStatus status;

    @Lob
    @Column(columnDefinition = "text")
    String description;

    @OneToMany(mappedBy = "item",fetch = FetchType.EAGER) //DONE
    List<Image> images;

    @ManyToOne()
    @JoinColumn(name = "order_id")
    Order order;


    //Fetch type eager so that app can suggest related products
    @ManyToMany(mappedBy = "items",fetch = FetchType.EAGER)
    List<Tag> tags;

    //Fetch type eager so that app can suggest related products
    @ManyToOne(fetch = FetchType.EAGER) //DONE
    @JoinColumn(name = "item_type_id")
    ItemType itemType;

    Float height;

    Float width;

    @CreatedDate
    OffsetDateTime createdDate;

}
