package com.Inventorymanagement.userservice.feign;



import com.Inventorymanagement.userservice.config.FeignConfig;
import com.Inventorymanagement.userservice.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product-service", configuration = FeignConfig.class)
public interface ProductClient {

    @GetMapping("/api/products")
    List<ProductResponse> getAllProducts();
}
