package org.example.productstore.mapper;

import org.example.productstore.dto.OrderDTO;
import org.example.productstore.entity.ItemEntity;
import org.example.productstore.entity.OrderEntity;

public class OrderMapper {

    public static OrderDTO toDTO(OrderEntity order) {
        return OrderDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .itemsId(order.getItems().stream().map(ItemEntity::getId).toList())
                .build();
    }

    public static OrderEntity toEntity(OrderDTO dto) {
        return OrderEntity.builder()
                .id(dto.getId())
                .orderNumber(dto.getOrderNumber())
                .build();
    }
}
