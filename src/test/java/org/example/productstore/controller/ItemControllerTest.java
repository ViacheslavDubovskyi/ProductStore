package org.example.productstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.productstore.controller.impl.ItemControllerBean;
import org.example.productstore.dto.ItemDTO;
import org.example.productstore.service.ItemService;
import org.example.productstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemControllerBean.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    private ItemService itemService;

    @MockitoBean
    private OrderService orderService;

    @Test
    public void getItemById() throws Exception {

        ItemDTO item = ItemDTO.builder()
                .id(1)
                .orderId(123)
                .productId(10)
                .quantity(2)
                .build();

        when(itemService.findById(1)).thenReturn(item);

        mockMvc.perform(get("/items/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderId").value(123))
                .andExpect(jsonPath("$.productId").value(10))
                .andExpect(jsonPath("$.quantity").value(2));
    }

    @Test
    void saveItem() throws Exception {
        ItemDTO saved = ItemDTO.builder()
                .id(1)
                .orderId(123)
                .productId(10)
                .build();

        when(itemService.save(Mockito.any())).thenReturn(saved);
        String json = mapper.writeValueAsString(saved);
        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderId").value(123))
                .andExpect(jsonPath("$.productId").value(10));
    }

    @Test
    void changeQuantity_ok() throws Exception {
        ItemDTO updatedItem = ItemDTO.builder()
                .id(1)
                .orderId(123)
                .productId(10)
                .quantity(5)
                .build();

        Mockito.when(itemService.changeQuantity(1, 5)).thenReturn(updatedItem);
        mockMvc.perform(patch("/items/{itemId}", 1)
                        .param("newQuantity", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderId").value(123))
                .andExpect(jsonPath("$.productId").value(10))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    void deleteItem_ok() throws Exception {
        Mockito.doNothing().when(orderService).removeItem(1, 123);

        mockMvc.perform(delete("/items/{itemId}", 1)
                        .param("orderId", "123"))
                .andExpect(status().isOk());

        Mockito.verify(orderService, Mockito.times(1)).removeItem(1, 123);
    }

    @Test
    void getItemById_notFound() throws Exception {
        Mockito.when(itemService.findById(1))
                .thenThrow(new EntityNotFoundException("Item not found"));

        mockMvc.perform(get("/items/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteItem_notFound() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("Item or order not found"))
                .when(orderService).removeItem(1, 123);

        mockMvc.perform(delete("/items/{itemId}", 1)
                        .param("orderId", "123"))
                .andExpect(status().isNotFound());

        Mockito.verify(orderService, Mockito.times(1)).removeItem(1, 123);
    }
}
