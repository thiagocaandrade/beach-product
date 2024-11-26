package com.salsatechnology.service;

import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.exception.ExistsException;
import com.salsatechnology.exception.NotFoundException;
import com.salsatechnology.mapper.ProductOrderMapper;
import com.salsatechnology.model.ProductOrder;
import com.salsatechnology.repository.ProductOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductOrderServiceTest {

    @Mock
    private ProductOrderRepository productOrderRepository;

    @Mock
    private ProductOrderMapper productOrderMapper;

    @InjectMocks
    private ProductOrderService productOrderService;

    @Test
    void testCreateOrder_WhenOrderExists() {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setUserName("testUser");

        when(productOrderRepository.existsByUserName("testUser")).thenReturn(true);

        assertThatThrownBy(() -> productOrderService.createOrder(productOrderDTO))
                .isInstanceOf(ExistsException.class)
                .hasMessage("Pedido já existe com id: testUser");

        verify(productOrderRepository, never()).save(any(ProductOrder.class));
    }

    @Test
    void testCreateOrder_WhenOrderDoesNotExist() {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setUserName("testUser");
        ProductOrder productOrder = new ProductOrder();

        when(productOrderRepository.existsByUserName("testUser")).thenReturn(false);
        when(productOrderMapper.toProductOrder(productOrderDTO)).thenReturn(productOrder);

        productOrderService.createOrder(productOrderDTO);

        verify(productOrderRepository).save(productOrder);
    }

    @Test
    void testGetOrders_WhenOrdersExist() {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setUserName("testUser");

        ProductOrder productOrder = new ProductOrder();
        productOrder.setUserAmount(100L);

        List<ProductOrder> orders = Collections.singletonList(productOrder);

        when(productOrderRepository.findByUserName("testUser")).thenReturn(orders);

        List<ProductOrder> result = productOrderService.getOrders("testUser");

        assertThat(result).isEqualTo(orders);
        assertThat(result.get(0).getUserAmount()).isEqualTo(1L);
    }

    @Test
    void testGetOrders_WhenOrdersDoNotExist() {
        when(productOrderRepository.findByUserName("testUser")).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> productOrderService.getOrders("testUser"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Nenhum pedido encontrado para o usuário: testUser");
    }
}