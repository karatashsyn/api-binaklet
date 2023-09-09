package com.binaklet.binaklet.requests;

import lombok.Data;

@Data
public class OrderCreateRequest {
    Long pickUpAddressId;
    Long deliverAddressId;

    Long[]itemsId;

    Long buyerId;

    Long sellerId;

    Long transporterId;

}
