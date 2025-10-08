package org.example.productstore.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {

    private int id;
    private String name;
    private double price;
    private String description;
}
