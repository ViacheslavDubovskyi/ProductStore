package org.example.productstore.controller;

import org.example.productstore.dto.ItemDTO;

import java.util.List;

public interface ItemController {

    List<ItemDTO> findAll();

    ItemDTO save(ItemDTO itemDTO);

    ItemDTO findById(int id);

    ItemDTO changeQuantity(int itemId, int newQuantity);

    void deleteItem(int itemId, int orderId);
}
