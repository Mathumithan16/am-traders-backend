package com.amtraders.oms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDto {
    private BigDecimal totalAmount;
    private Long supplierId;
    private List<PurchaseItemRequestDto> items;
}
