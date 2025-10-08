package org.example.productstore.service.impl;

import lombok.AllArgsConstructor;
import org.example.productstore.dto.ProductDTO;
import org.example.productstore.entity.ProductEntity;
import org.example.productstore.mapper.ProductMapper;
import org.example.productstore.repository.ProductRepository;
import org.example.productstore.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceBean implements ProductService {

    private ProductRepository productRepository;

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(ProductMapper::toDTO).toList();
    }

    public ProductDTO save(ProductDTO productDTO) {
        ProductEntity productEntity = ProductMapper.toEntity(productDTO);
        productEntity.setCreationDate(LocalDate.now());
        productEntity = productRepository.save(productEntity);
        return ProductMapper.toDTO(productEntity);
    }

    public void remove(int id) {
        productRepository.deleteById(id);
    }

    public ProductDTO findById(int id) {
        return productRepository.findById(id).map(ProductMapper::toDTO).orElseThrow();
    }
}
