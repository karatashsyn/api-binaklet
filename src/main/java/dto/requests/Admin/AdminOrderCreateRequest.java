package dto.requests.Admin;


import lombok.Data;

@Data
public class AdminOrderCreateRequest {
    String pickUpAddress;
    String deliverAddress;
    Long[]itemIds;
    Long sellerId;
    Long buyerId;
}
