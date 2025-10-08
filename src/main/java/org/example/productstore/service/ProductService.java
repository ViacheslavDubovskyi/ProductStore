package org.example.productstore.service;

import org.example.productstore.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    public List<ProductDTO> findAll();

    public ProductDTO save(ProductDTO productDTO);

    public void remove(int id);

    public ProductDTO findById(int id);
}
