package dto.requests.order;

import lombok.Data;

@Data
public class OrderCreateRequest {
    String pickupAddress;
    String deliverAddress;
    Long buyerId;
    Long[]itemIds;
    Long sellerId;
}
