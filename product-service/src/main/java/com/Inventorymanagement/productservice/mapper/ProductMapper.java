package com.Inventorymanagement.productservice.mapper;


import com.Inventorymanagement.productservice.dto.ProductRequest;
import com.Inventorymanagement.productservice.dto.ProductResponse;
import com.Inventorymanagement.productservice.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}
