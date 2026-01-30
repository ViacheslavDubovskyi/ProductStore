package org.example.productstore.controller;

import org.example.productstore.dto.ProductDTO;

import java.util.List;

public interface ProductController {

    List<ProductDTO> findAll();

    ProductDTO findById(int id);

    ProductDTO save(ProductDTO productDTO);

    void delete(int id);
}
