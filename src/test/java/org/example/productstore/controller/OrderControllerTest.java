package org.example.productstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.productstore.controller.impl.OrderControllerBean;
import org.example.productstore.dto.ItemDTO;
import org.example.productstore.dto.OrderDTO;
import org.example.productstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderControllerBean.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    private OrderService orderService;

    @Test
    public void getOrderById() throws Exception {

        OrderDTO order = OrderDTO.builder()
                .id(1)
                .orderNumber(123)
                .itemsId(List.of(10, 20))
                .build();

        when(orderService.findById(1)).thenReturn(order);
        mockMvc.perform(get("/orders/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderNumber").value(123))
                .andExpect(jsonPath("$.itemsId[0]").value(10))
                .andExpect(jsonPath("$.itemsId[1]").value(20));
    }

    @Test
    void saveOrder() throws Exception {
        OrderDTO saved = OrderDTO.builder()
                .id(1)
                .orderNumber(123)
                .build();

        when(orderService.save(Mockito.any())).thenReturn(saved);
        String json = mapper.writeValueAsString(saved);
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderNumber").value(123));
    }

    @Test
    void addItem() throws Exception {
        ItemDTO item = ItemDTO.builder().id(1).productId(111).build();
        OrderDTO updatedOrder = OrderDTO.builder()
                .id(1)
                .orderNumber(123)
                .itemsId(List.of(item.getId()))
                .build();

        Mockito.when(orderService.addItem(Mockito.any(), Mockito.eq(1))).thenReturn(updatedOrder);
        String jsonItem = mapper.writeValueAsString(item);

        mockMvc.perform(post("/orders/{id}/item", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonItem))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderNumber").value(123))
                .andExpect(jsonPath("$.itemsId[0]").value(1));
    }

    @Test
    void delete_order() throws Exception {
        Mockito.doNothing().when(orderService).deleteOrder(1);

        Mockito.doNothing().when(orderService).deleteOrder(1);
        mockMvc.perform(delete("/orders/{id}", 1))
                .andExpect(status().isOk());

        Mockito.verify(orderService, Mockito.times(1)).deleteOrder(1);
    }

    @Test
    void remove_item() throws Exception {
        Mockito.doNothing().when(orderService).removeItem(1, 10);

        mockMvc.perform(delete("/orders/{orderId}/item/{itemId}", 1, 10))
                .andExpect(status().isOk());

        Mockito.verify(orderService, Mockito.times(1)).removeItem(1, 10);
    }

    @Test
    void getOrderById_notFound() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("Order not found"))
                .when(orderService).findById(1);

        mockMvc.perform(get("/orders/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteOrder_notFound() throws Exception {
        Mockito.doThrow(new EntityNotFoundException("Order not found"))
                .when(orderService).deleteOrder(1);

        mockMvc.perform(delete("/orders/{id}", 1))
                .andExpect(status().isNotFound());

        Mockito.verify(orderService, Mockito.times(1)).deleteOrder(1);
    }
}
