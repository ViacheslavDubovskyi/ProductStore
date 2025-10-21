package org.example.productstore.controller;

import org.example.productstore.dto.ItemDTO;

import java.util.List;

public interface ItemController {

    public List<ItemDTO> findAll();

    public ItemDTO save(ItemDTO itemDTO);

    public ItemDTO findById(int id);

    public ItemDTO changeQuantity(int itemId, int newQuantity);

    public void deleteItem(int itemId, int orderId);
}
