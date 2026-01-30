package org.example.productstore.service;

import org.example.productstore.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAll();

    ProductDTO save(ProductDTO productDTO);

    void remove(int id);

    ProductDTO findById(int id);
}
