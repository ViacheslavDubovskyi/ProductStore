package org.example.productstore.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDTO {

    private int orderId;
    private int productId;
    private int quantity;
}
