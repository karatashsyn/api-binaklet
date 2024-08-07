package dto.requests.cart;
import lombok.Data;
import java.util.List;

@Data

public class AddToCartRequest {

    List<Long> itemIds;

}