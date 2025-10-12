package org.example.productstore.service;

import org.example.productstore.dto.ItemDTO;
import org.example.productstore.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    public List<OrderDTO> findAll();

    public OrderDTO save(OrderDTO orderDTO);

    public OrderDTO addItem(ItemDTO itemDTO, int orderId);

    public OrderDTO findById(int id);

    public void deleteOrder(int orderId);

    public void removeItem(int itemId);
}
