package com.binaklet.binaklet.dto.requests.admin;


import lombok.Data;

@Data
public class AdminOrderCreateRequest {
    Long pickUpAddressId;
    Long deliverAddressId;
    Long[]itemIds;
    Long sellerId;
    Long buyerId;
}
