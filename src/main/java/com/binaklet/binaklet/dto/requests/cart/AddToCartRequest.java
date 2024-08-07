package com.binaklet.binaklet.dto.requests.cart;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data

public class AddToCartRequest {

    @NotEmpty(message = "Sepete en az bir ürün eklemelisiniz.")
    List<Long> itemIds;

}