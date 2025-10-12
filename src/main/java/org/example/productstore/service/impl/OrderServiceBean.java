package org.example.productstore.service.impl;

import lombok.AllArgsConstructor;
import org.example.productstore.dto.ItemDTO;
import org.example.productstore.dto.OrderDTO;
import org.example.productstore.entity.ItemEntity;
import org.example.productstore.entity.OrderEntity;
import org.example.productstore.entity.ProductEntity;
import org.example.productstore.mapper.ItemMapper;
import org.example.productstore.mapper.OrderMapper;
import org.example.productstore.repository.ItemRepository;
import org.example.productstore.repository.OrderRepository;
import org.example.productstore.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceBean implements OrderService {

    private OrderRepository orderRepository;
    private ItemRepository itemRepository;

    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream().map(OrderMapper::toDTO).toList();
    }

    public OrderDTO save(OrderDTO orderDTO) {
        return OrderMapper.toDTO(orderRepository.save(OrderMapper.toEntity(orderDTO)));
    }

    public OrderDTO addItem(ItemDTO itemDTO, int orderId) {
        OrderEntity orderBuild = OrderEntity.builder().id(orderId).build();
        ItemEntity itemEntity = ItemMapper.toEntity(itemDTO);
        itemEntity.setOrder(orderBuild);

        ProductEntity productBuild = ProductEntity.builder().id(itemEntity.getProduct().getId()).build();
        itemEntity.setProduct(productBuild);

        orderBuild.getItems().add(itemEntity);
        orderRepository.save(orderBuild);
        return findById(orderId);
    }

    public OrderDTO findById(int id) {
        return orderRepository.findById(id).map(OrderMapper::toDTO).orElseThrow();
    }

    public void removeItem(int itemId) {
        itemRepository.deleteById(itemId);
    }

    public void deleteOrder(int orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow();
        orderRepository.delete(orderEntity);
    }
}