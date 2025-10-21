package org.example.productstore.controller;

import org.example.productstore.dto.ItemDTO;
import org.example.productstore.dto.OrderDTO;

import java.util.List;

public interface OrderController {

    public List<OrderDTO> findAll();

    public OrderDTO findById(int id);

    public OrderDTO save(OrderDTO orderDTO);

    public OrderDTO addItem(ItemDTO itemDTO, int orderId);

    public void removeItem(int orderId, int itemId);

    public void deleteOrder(int id);
}
