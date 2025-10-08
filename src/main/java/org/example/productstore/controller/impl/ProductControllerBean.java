package org.example.productstore.controller.impl;

import lombok.AllArgsConstructor;
import org.example.productstore.controller.ProductController;
import org.example.productstore.dto.ProductDTO;
import org.example.productstore.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/products")
@AllArgsConstructor
public class ProductControllerBean implements ProductController {

    private ProductService productService;

    @GetMapping
    public List<ProductDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable int id) {
        return productService.findById(id);
    }

    @PostMapping
    public ProductDTO save(@RequestBody ProductDTO productDTO) {
        return productService.save(productDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        productService.remove(id);
    }
}
