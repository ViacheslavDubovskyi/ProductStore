package org.example.productstore.controller.impl;

import lombok.AllArgsConstructor;
import org.example.productstore.dto.ItemDTO;
import org.example.productstore.dto.OrderDTO;
import org.example.productstore.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/orders")
@AllArgsConstructor
public class OrderControllerBean {

    private OrderService orderService;

    @GetMapping
    public List<OrderDTO> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderDTO findById(@PathVariable int id) {
        return orderService.findById(id);
    }

    @PostMapping
    public OrderDTO save(@RequestBody OrderDTO orderDTO) {
        return orderService.save(orderDTO);
    }

    @PostMapping("/{id}/item")
    public OrderDTO addItem(@RequestBody ItemDTO itemDTO, @PathVariable("id") int orderId) {
        return orderService.addItem(itemDTO, orderId);
    }

    @DeleteMapping("/{orderId}/item/{itemId}")
    public void removeItem(@PathVariable int orderId, @PathVariable int itemId) {
        orderService.removeItem(orderId, itemId);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }
}