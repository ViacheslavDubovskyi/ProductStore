package org.example.productstore.controller;

import org.example.productstore.dto.ProductDTO;

import java.util.List;

public interface ProductController {

    public List<ProductDTO> findAll();

    public ProductDTO findById(int id);

    public ProductDTO save(ProductDTO productDTO);

    public void delete(int id);
}
