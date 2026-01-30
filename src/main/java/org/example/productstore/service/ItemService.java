package org.example.productstore.service;

import org.example.productstore.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    List<ItemDTO> findAll();

    ItemDTO save(ItemDTO itemDTO);

    ItemDTO findById(int id);

    ItemDTO changeQuantity(int itemId, int newQuantity);
}
