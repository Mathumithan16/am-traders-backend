package com.amtraders.oms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemRequestDto {
    private Integer quantity;
    private BigDecimal buyPrice;
    private BigDecimal subtotal;
    private Long productId;
}
