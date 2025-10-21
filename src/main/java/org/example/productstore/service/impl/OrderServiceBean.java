package org.example.productstore.service.impl;

import lombok.AllArgsConstructor;
import org.example.productstore.dto.ItemDTO;
import org.example.productstore.dto.OrderDTO;
import org.example.productstore.entity.ItemEntity;
import org.example.productstore.entity.OrderEntity;
import org.example.productstore.entity.ProductEntity;
import org.example.productstore.mapper.ItemMapper;
import org.example.productstore.mapper.OrderMapper;
import org.example.productstore.repository.OrderRepository;
import org.example.productstore.repository.ProductRepository;
import org.example.productstore.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceBean implements OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream().map(OrderMapper::toDTO).toList();
    }

    public OrderDTO save(OrderDTO orderDTO) {
        return OrderMapper.toDTO(orderRepository.save(OrderMapper.toEntity(orderDTO)));
    }

    public OrderDTO addItem(ItemDTO itemDTO, int orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow();
        ItemEntity itemEntity = ItemMapper.toEntity(itemDTO);
        ProductEntity product = productRepository.findById(itemDTO.getProductId()).orElseThrow();

        itemEntity.setProduct(product);
        itemEntity.setOrder(order);

        order.getItems().add(itemEntity);
        orderRepository.save(order);

        return findById(orderId);
    }

    public OrderDTO findById(int id) {
        return orderRepository.findById(id).map(OrderMapper::toDTO).orElseThrow();
    }

    public void removeItem(int itemId, int orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow();
        order.getItems().removeIf(item -> item.getId() == itemId);
        orderRepository.save(order);
    }

    public void deleteOrder(int orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow();
        orderRepository.delete(orderEntity);
    }
}