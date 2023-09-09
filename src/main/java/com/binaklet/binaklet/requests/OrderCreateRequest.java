package com.binaklet.binaklet.requests;

import lombok.Data;

@Data
public class OrderCreateRequest {
    Long pickUpAddressId;
    Long deliverAddressId;

    Long[]itemIds;

    Long sellerId;

    Long transporterId;

}
