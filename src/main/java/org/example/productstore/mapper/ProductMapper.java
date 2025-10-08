package org.example.productstore.mapper;

import org.example.productstore.dto.ProductDTO;
import org.example.productstore.entity.ProductEntity;

public class ProductMapper {

    public static ProductDTO toDTO(ProductEntity productEntity) {
        return ProductDTO.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .description(productEntity.getDescription())
                .build();
    }

    public static ProductEntity toEntity(ProductDTO dto) {
        return ProductEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }
}
