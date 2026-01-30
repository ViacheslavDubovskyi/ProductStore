package org.example.productstore.service;

import org.example.productstore.dto.ItemDTO;
import org.example.productstore.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> findAll();

    OrderDTO save(OrderDTO orderDTO);

    OrderDTO addItem(ItemDTO itemDTO, int orderId);

    OrderDTO findById(int id);

    void deleteOrder(int orderId);

    void removeItem(int itemId, int orderId);
}
