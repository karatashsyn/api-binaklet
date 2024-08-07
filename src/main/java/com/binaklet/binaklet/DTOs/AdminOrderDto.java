package com.binaklet.binaklet.DTOs;

import com.binaklet.binaklet.entities.Payment;
import com.binaklet.binaklet.enums.OrderStatus;
import dto.responses.item.BasicItemDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class AdminOrderDto {
    public Long id;

    OrderStatus status;
    Date createdDate;
    Payment payment;
    String pickUpAddress;
    String deliverAddress;
    BasicUserDto buyer;
    BasicUserDto seller;
    List<BasicItemDTO> items;

}
