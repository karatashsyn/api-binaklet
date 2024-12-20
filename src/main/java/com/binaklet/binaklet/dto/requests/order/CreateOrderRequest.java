package com.binaklet.binaklet.dto.requests.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequest {
    @NotNull(message = "Alış adres id'si boş olamaz.")
    Long pickupAddressId;

    @NotNull(message = "Çıkış adres id'si boş olamaz.")
    Long deliverAddressId;

    @NotNull(message = "Satıcı id'si boş olamaz.")
    Long sellerId;

    @NotEmpty(message = "Sipariş en az bir ürün içermeli.")
    Long[]itemIds;
}
