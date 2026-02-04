package org.example.productstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.productstore.controller.impl.ProductControllerBean;
import org.example.productstore.dto.ProductDTO;
import org.example.productstore.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductControllerBean.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    private ProductService productService;

    @Test
    public void getProductById() throws Exception {

        ProductDTO productDTO = ProductDTO.builder()
                .id(1)
                .name("phone")
                .build();

        when(productService.findById(1)).thenReturn(productDTO);
        mockMvc.perform(get("/products/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("phone"));
    }

    @Test
    public void addProduct() throws Exception {
        ProductDTO saved = ProductDTO.builder()
                .id(1)
                .name("phone")
                .description("Apple phone")
                .price(1000.0)
                .build();

        String json = mapper.writeValueAsString(saved);
        when(productService.save(Mockito.any())).thenReturn(saved);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("phone"));
    }

    @Test
    void findAll() throws Exception {
        List<ProductDTO> products = List.of(
                ProductDTO.builder().id(1).name("phone").build(),
                ProductDTO.builder().id(2).name("tablet").build()
        );

        when(productService.findAll()).thenReturn(products);
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("phone"));
    }

    @Test
    void delete_ok() throws Exception {
        Mockito.doNothing().when(productService).remove(1);

        mockMvc.perform(delete("/products/{id}", 1))
                .andExpect(status().isOk());

        Mockito.verify(productService).remove(1);
    }

    @Test
    void not_found() throws Exception {
        when(productService.findById(1))
                .thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/products/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_notFound() throws Exception {
        doThrow(new EntityNotFoundException("Product with id=111 not found"))
                .when(productService)
                .remove(111);
        mockMvc.perform(delete("/products/{id}", 111))
                .andExpect(status().isNotFound());
    }
}
