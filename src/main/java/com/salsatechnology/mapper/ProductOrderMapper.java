package com.salsatechnology.mapper;

import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.exception.ProductOrderException;
import com.salsatechnology.model.ProductOrder;
import com.salsatechnology.model.ProductType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface ProductOrderMapper {


    @Mapping(target = "productValue", expression = "java(getProductValue(productOrderDTO.getProductType()) * 100L)")
    @Mapping(target = "productTotal", expression = "java(getProductValue(productOrderDTO.getProductType()) * productOrderDTO.getTimeHour() * 100L)")
    @Mapping(target = "userAmount", expression = "java(calculateUserAmount(getProductValue(productOrderDTO.getProductType()) * productOrderDTO.getTimeHour(), productOrderDTO.getProductType()) * 100L)")
    ProductOrder toProductOrder(ProductOrderDTO productOrderDTO);

    Map<ProductType, Integer> PRODUCT_VALUES = Map.of(
            ProductType.SURFBOARD, 50,
            ProductType.BEACH_CHAIR, 35,
            ProductType.SUNSHADE, 40,
            ProductType.SAND_BOARD, 25,
            ProductType.BEACH_TABLE, 25
    );

    Map<ProductType, Double> USER_AMOUNT_MULTIPLIERS = Map.of(
            ProductType.SURFBOARD, 0.156,
            ProductType.BEACH_CHAIR, 0.05,
            ProductType.SUNSHADE, 0.103,
            ProductType.SAND_BOARD, 0.09,
            ProductType.BEACH_TABLE, 0.081
    );

    default int getProductValue(ProductType productType) {
        Integer value = PRODUCT_VALUES.get(productType);
        if (value == null) {
            throw new ProductOrderException("Tipo de produto desconhecido: " + productType);
        }
        return value;
    }

    default int calculateUserAmount(int productTotal, ProductType productType) {
        Double multiplier = USER_AMOUNT_MULTIPLIERS.get(productType);
        if (multiplier == null) {
            throw new ProductOrderException("Tipo de produto desconhecido: " + productType);
        }
        return (int) (productTotal * multiplier);
    }
}