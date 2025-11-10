package org.example.productstore.service;

import org.example.productstore.dto.ProductDTO;
import org.example.productstore.entity.ProductEntity;
import org.example.productstore.mapper.ProductMapper;
import org.example.productstore.repository.ProductRepository;
import org.example.productstore.service.impl.ProductServiceBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceBean service;

    @Test
    public void saveProduct() {

        ProductDTO product = ProductDTO.builder().id(10).build();
        ProductEntity entity = ProductMapper.toEntity(product);
        when(repository.save(any(ProductEntity.class))).thenReturn(entity);

        assertEquals(product.getId(), service.save(product).getId());
        Mockito.verify(repository).save(any(ProductEntity.class));
    }

    @Test
    public void getAllProducts() {

        ProductDTO product1 = ProductDTO.builder().id(1).build();
        ProductDTO product2 = ProductDTO.builder().id(2).build();
        List<ProductDTO> productDTOList = Arrays.asList(product1, product2);

        ProductEntity entity1 = ProductMapper.toEntity(product1);
        ProductEntity entity2 = ProductMapper.toEntity(product2);
        List<ProductEntity> productEntityList = Arrays.asList(entity1, entity2);

        when(repository.findAll()).thenReturn(productEntityList);
        assertEquals(productDTOList, service.findAll());
        Mockito.verify(repository).findAll();
    }

    @Test
    public void getProductById() {
        ProductDTO product = ProductDTO.builder().id(1).build();
        ProductEntity entity = ProductMapper.toEntity(product);
        when(repository.findById(any())).thenReturn(Optional.of(entity));
        assertEquals(product.getId(), service.findById(1).getId());
        Mockito.verify(repository).findById(any());
    }

    @Test
    public void deleteProductById() {
        ProductRepository spy = Mockito.spy(ProductRepository.class);
        ProductServiceBean spyService = new ProductServiceBean(spy);
        spyService.remove(111);
        Mockito.verify(spy).deleteById(111);
    }
}
