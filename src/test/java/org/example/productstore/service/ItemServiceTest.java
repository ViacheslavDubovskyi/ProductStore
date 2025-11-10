package org.example.productstore.service;

import org.example.productstore.dto.ItemDTO;
import org.example.productstore.entity.ItemEntity;
import org.example.productstore.mapper.ItemMapper;
import org.example.productstore.repository.ItemRepository;
import org.example.productstore.service.impl.ItemServiceBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository repository;

    @InjectMocks
    private ItemServiceBean service;

    @Test
    public void saveItem() {

        ItemDTO item = ItemDTO.builder().id(10).build();
        ItemEntity entity = ItemMapper.toEntity(item);
        when(repository.save(any(ItemEntity.class))).thenReturn(entity);

        assertEquals(item.getId(), service.save(item).getId());
        verify(repository).save(any(ItemEntity.class));
    }

    @Test
    public void getAllProducts() {

        ItemDTO item1 = ItemDTO.builder().id(1).build();
        ItemDTO item2 = ItemDTO.builder().id(2).build();
        List<ItemDTO> itemDTOList = Arrays.asList(item1, item2);

        ItemEntity entity1 = ItemMapper.toEntity(item1);
        ItemEntity entity2 = ItemMapper.toEntity(item2);
        List<ItemEntity> itemEntityList = Arrays.asList(entity1, entity2);

        when(repository.findAll()).thenReturn(itemEntityList);
        assertEquals(itemDTOList, service.findAll());
        verify(repository).findAll();
    }

    @Test
    public void getProductById() {
        ItemDTO item = ItemDTO.builder().id(1).build();
        ItemEntity entity = ItemMapper.toEntity(item);
        when(repository.findById(any())).thenReturn(Optional.of(entity));
        assertEquals(item.getId(), service.findById(1).getId());
        verify(repository).findById(any());
    }

    @Test
    public void changeQuantityofItem() {
        ItemEntity entity = ItemEntity.builder().id(1).quantity(2).build();
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(repository.save(any(ItemEntity.class))).thenReturn(entity);

        ItemDTO updated = service.changeQuantity(1, 3);

        assertEquals(3, updated.getQuantity());
        verify(repository).findById(1);
        verify(repository).save(entity);
    }
}
