package com.amtraders.oms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private String size;
    private BigDecimal buyPrice;
    private BigDecimal sellingPrice;
    private Integer stockQuantity;
    private Long supplierId;
    private Long categoryId;
}
