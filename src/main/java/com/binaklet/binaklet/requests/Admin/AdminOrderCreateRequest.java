package com.binaklet.binaklet.requests.Admin;


import lombok.Data;

@Data
public class AdminOrderCreateRequest {
    Long pickUpAddressId;
    Long deliverAddressId;
    Long[]itemIds;
    Long sellerId;
    Long buyerId;
}
