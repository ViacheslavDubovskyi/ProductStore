package org.example.productstore.controller.impl;

import lombok.AllArgsConstructor;
import org.example.productstore.controller.ItemController;
import org.example.productstore.dto.ItemDTO;
import org.example.productstore.service.ItemService;
import org.example.productstore.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/items")
@AllArgsConstructor
public class ItemControllerBean implements ItemController {

    private ItemService itemService;
    private OrderService orderService;

    @GetMapping
    public List<ItemDTO> findAll() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ItemDTO findById(@PathVariable int id) {
        return itemService.findById(id);
    }

    @PostMapping
    public ItemDTO save(@RequestBody ItemDTO itemDTO) {
        return itemService.save(itemDTO);
    }

    @PatchMapping("/{itemId}")
    public ItemDTO changeQuantity(@PathVariable("itemId") int itemId, @RequestParam("newQuantity") int newQuantity) {
        return itemService.changeQuantity(itemId, newQuantity);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable int itemId, @RequestParam int orderId) {
        orderService.removeItem(itemId, orderId);
    }
}
