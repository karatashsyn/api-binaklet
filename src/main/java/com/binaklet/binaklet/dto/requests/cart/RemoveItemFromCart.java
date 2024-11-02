package com.binaklet.binaklet.dto.requests.cart;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data

public class RemoveItemFromCart {
    @NotNull(message = "Ürün id'si boş olamaz.")
    Long itemId;
}