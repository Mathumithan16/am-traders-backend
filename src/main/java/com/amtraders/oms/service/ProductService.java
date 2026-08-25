package com.amtraders.oms.service;

import com.amtraders.oms.dto.request.ProductRequestDto;
import com.amtraders.oms.dto.response.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> getAllProducts();

    Optional<ProductResponseDto> getProductById(Long id);

    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto);

    void deleteProduct(Long id);

}
