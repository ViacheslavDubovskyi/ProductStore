package org.example.productstore.mapper;

import org.example.productstore.dto.ItemDTO;
import org.example.productstore.entity.ItemEntity;

public class ItemMapper {

    public static ItemDTO toDTO(ItemEntity item) {
        return ItemDTO.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .build();
    }

    public static ItemEntity toEntity(ItemDTO item) {
        return ItemEntity.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .build();
    }
}
