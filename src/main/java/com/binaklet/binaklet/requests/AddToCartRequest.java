package com.binaklet.binaklet.requests;
import lombok.Data;
import java.util.List;

@Data

public class AddToCartRequest {

    List<Long> itemIds;

}