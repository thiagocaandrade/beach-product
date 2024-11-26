package com.salsatechnology.service;

import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.exception.ExistsException;
import com.salsatechnology.exception.NotFoundException;
import com.salsatechnology.mapper.ProductOrderMapper;
import com.salsatechnology.model.ProductOrder;
import com.salsatechnology.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOrderService {

	private final ProductOrderRepository productOrderRepository;
	private final ProductOrderMapper productOrderMapper;

	@Transactional
	public void createOrder(ProductOrderDTO productOrderDTO) {
		validateOrder(productOrderDTO.getUserName());
		ProductOrder productOrder = productOrderMapper.toProductOrder(productOrderDTO);

		productOrderRepository.save(productOrder);
	}

	public List<ProductOrder> getOrders(String userName) {
		List<ProductOrder> orders = productOrderRepository.findByUserName(userName);
		if (orders.isEmpty()) {
			throw new NotFoundException("Nenhum pedido encontrado para o usuário: " + userName);
		}
		return orders;
	}

	public List<ProductOrder> getFilteredOrders(String filterField, String filterValue) {
		List<ProductOrder> orders = productOrderRepository.findByFilterField(filterField, filterValue);
		if (orders.isEmpty()) {
			throw new NotFoundException("Nenhum pedido encontrado para o filtro: " + filterField + " com valor: " + filterValue);
		}
		return orders;
	}

	private void validateOrder(String userName) {
		if (productOrderRepository.existsByUserName(userName)) {
			throw new ExistsException("Pedido já existe com id: " + userName);
		}
	}
}