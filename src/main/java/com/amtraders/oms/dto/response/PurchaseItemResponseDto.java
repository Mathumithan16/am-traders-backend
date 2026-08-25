package com.amtraders.oms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemResponseDto {
    private Long id;
    private Integer quantity;
    private BigDecimal buyPrice;
    private BigDecimal subtotal;
    private Long productId;
}
