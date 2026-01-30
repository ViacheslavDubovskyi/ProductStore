package org.example.productstore.controller;

import org.example.productstore.dto.ItemDTO;
import org.example.productstore.dto.OrderDTO;

import java.util.List;

public interface OrderController {

    List<OrderDTO> findAll();

    OrderDTO findById(int id);

    OrderDTO save(OrderDTO orderDTO);

    OrderDTO addItem(ItemDTO itemDTO, int orderId);

    void removeItem(int orderId, int itemId);

    void deleteOrder(int id);
}
