package org.example.productstore.service;

import org.example.productstore.dto.ItemDTO;
import org.example.productstore.dto.OrderDTO;
import org.example.productstore.entity.ItemEntity;
import org.example.productstore.entity.OrderEntity;
import org.example.productstore.entity.ProductEntity;
import org.example.productstore.mapper.OrderMapper;
import org.example.productstore.repository.OrderRepository;
import org.example.productstore.repository.ProductRepository;
import org.example.productstore.service.impl.OrderServiceBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceBean service;

    @Test
    public void saveOrder() {

        OrderDTO order = OrderDTO.builder().id(10).build();
        OrderEntity entity = OrderMapper.toEntity(order);
        entity.setItems(new ArrayList<>());
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(entity);

        assertEquals(order.getId(), service.save(order).getId());
        Mockito.verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
    public void getAllOrders() {

        OrderDTO order1 = OrderDTO.builder().id(1).itemsId(new ArrayList<>()).build();
        OrderDTO order2 = OrderDTO.builder().id(2).itemsId(new ArrayList<>()).build();
        List<OrderDTO> orderDTOList = Arrays.asList(order1, order2);

        OrderEntity entity1 = OrderMapper.toEntity(order1);
        entity1.setItems(new ArrayList<>());

        OrderEntity entity2 = OrderMapper.toEntity(order2);
        entity2.setItems(new ArrayList<>());

        List<OrderEntity> orderEntityList = Arrays.asList(entity1, entity2);

        when(orderRepository.findAll()).thenReturn(orderEntityList);
        assertEquals(orderDTOList, service.findAll());
        Mockito.verify(orderRepository).findAll();
    }

    @Test
    public void getOrderById() {

        OrderDTO order = OrderDTO.builder().id(1).itemsId(new ArrayList<>()).build();
        OrderEntity entity = OrderMapper.toEntity(order);
        entity.setItems(new ArrayList<>());

        when(orderRepository.findById(any())).thenReturn(Optional.of(entity));
        assertEquals(order.getId(), service.findById(1).getId());
        Mockito.verify(orderRepository).findById(any());
    }

    @Test
    public void deleteOrderById() {
        OrderEntity order = new OrderEntity();
        order.setId(111);
        when(orderRepository.findById(111)).thenReturn(Optional.of(order));

        service.deleteOrder(111);
        Mockito.verify(orderRepository).delete(order);
    }

    @Test
    public void addItemToOrder() {

        int orderId = 15;
        OrderEntity order = OrderEntity.builder().id(orderId).items(new ArrayList<>()).build();
        ProductEntity product = ProductEntity.builder().id(10).build();
        ItemDTO itemDTO = ItemDTO.builder().id(2).productId(10).build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(productRepository.findById(itemDTO.getProductId())).thenReturn(Optional.of(product));
        when(orderRepository.save(order)).thenReturn(order);

        service.addItem(itemDTO, orderId);

        assertEquals(orderId, order.getId());
        assertEquals(1, order.getItems().size());
        assertEquals(product.getId(), order.getItems().get(0).getProduct().getId());

        Mockito.verify(orderRepository, Mockito.times(2)).findById(orderId);
        Mockito.verify(productRepository).findById(itemDTO.getProductId());
        Mockito.verify(orderRepository).save(order);
    }

    @Test
    public void removeItemFromOrder() {

        int orderId = 15;
        int itemId = 10;
        OrderEntity order = OrderEntity.builder().id(orderId).items(new ArrayList<>()).build();

        List<ItemEntity> entities = List.of(
                ItemEntity.builder().id(1).build(),
                ItemEntity.builder().id(5).build(),
                ItemEntity.builder().id(10).build());
        order.setItems(new ArrayList<>(entities));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        service.removeItem(itemId, orderId);

        assertEquals(orderId, order.getId());
        assertEquals(2, order.getItems().size());
        Mockito.verify(orderRepository).findById(orderId);
    }
}
