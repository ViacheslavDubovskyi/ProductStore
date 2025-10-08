package org.example.productstore.service.impl;

import lombok.AllArgsConstructor;
import org.example.productstore.repository.ItemRepository;
import org.example.productstore.service.ItemService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemServiceBean implements ItemService {

    private ItemRepository itemRepository;

}
