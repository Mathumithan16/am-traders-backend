package com.amtraders.oms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private String name;
    private String size;
    private BigDecimal buyPrice;
    private BigDecimal sellingPrice;
    private Integer stockQuantity;
    private Long supplierId;
    private Long categoryId;
}
