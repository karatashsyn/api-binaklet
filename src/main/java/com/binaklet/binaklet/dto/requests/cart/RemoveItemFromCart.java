package com.binaklet.binaklet.dto.requests.cart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;

@Data

public class RemoveItemFromCart {

    @NotNull(message = "Ürün id'si boş olamaz.")
    Long itemId;

}