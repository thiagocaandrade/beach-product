package com.salsatechnology.controller;

import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.exception.ExistsException;
import com.salsatechnology.exception.NotFoundException;
import com.salsatechnology.model.ProductOrder;
import com.salsatechnology.service.ProductOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductOrderController.class)
public class ProductOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductOrderService productOrderService;

    @Test
    public void testCreateOrder() throws Exception {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();

        mockMvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"testUser\",\"productType\":\"BEACH_CHAIR\",\"timeHour\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateOrder_WhenOrderExists() throws Exception {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setUserName("testUser");

        Mockito.doThrow(new ExistsException("Pedido já existe com id: testUser"))
                .when(productOrderService).createOrder(Mockito.any(ProductOrderDTO.class));

        mockMvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"testUser\",\"productType\":\"BEACH_CHAIR\",\"timeHour\":1}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetOrders() throws Exception {
        ProductOrder productOrder = new ProductOrder();
        Mockito.when(productOrderService.getOrders("testUser")).thenReturn(Collections.singletonList(productOrder));

        mockMvc.perform(get("/orders")
                        .param("userName", "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetOrders_WhenOrdersNotFound() throws Exception {
        Mockito.when(productOrderService.getOrders("testUser")).thenThrow(new NotFoundException("Nenhum pedido encontrado para o usuário: testUser"));

        mockMvc.perform(get("/orders")
                        .param("userName", "testUser"))
                .andExpect(status().isNotFound());
    }
}