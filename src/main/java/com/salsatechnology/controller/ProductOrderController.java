package com.salsatechnology.controller;

import javax.validation.Valid;

import com.salsatechnology.model.ProductOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.service.ProductOrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class ProductOrderController {
	
	@Autowired
	private ProductOrderService productOrderService;

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void createOrder(@RequestBody @Valid ProductOrderDTO productOrderDTO) {
		productOrderService.createOrder(productOrderDTO);
	}

	@GetMapping
	public List<ProductOrder> getOrders(@RequestParam String userName) {
		return productOrderService.getOrders(userName);
	}

	@GetMapping("/filter")
	public List<ProductOrder> getFilteredOrders(@RequestParam String filterField, @RequestParam String filterValue) {
		return productOrderService.getFilteredOrders(filterField, filterValue);
	}
}
