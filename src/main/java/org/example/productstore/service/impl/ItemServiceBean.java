package org.example.productstore.service.impl;

import lombok.AllArgsConstructor;
import org.example.productstore.dto.ItemDTO;
import org.example.productstore.entity.ItemEntity;
import org.example.productstore.mapper.ItemMapper;
import org.example.productstore.repository.ItemRepository;
import org.example.productstore.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceBean implements ItemService {

    private ItemRepository itemRepository;

    public List<ItemDTO> findAll() {
        return itemRepository.findAll().stream().map(ItemMapper::toDTO).toList();
    }

    public ItemDTO save(ItemDTO itemDTO) {
        return ItemMapper.toDTO(itemRepository.save(ItemMapper.toEntity(itemDTO)));
    }

    public ItemDTO findById(int id) {
        return itemRepository.findById(id).map(ItemMapper::toDTO).orElseThrow();
    }

    public ItemDTO changeQuantity(int itemId, int newQuantity) {
        ItemEntity item = itemRepository.findById(itemId).orElseThrow();
        item.setQuantity(newQuantity);
        return ItemMapper.toDTO(itemRepository.save(item));
    }
}
