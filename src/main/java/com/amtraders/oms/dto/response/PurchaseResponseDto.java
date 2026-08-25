package com.amtraders.oms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponseDto {
    private Long id;
    private LocalDateTime purchaseDate;
    private BigDecimal totalAmount;
    private Long supplierId;
    private List<PurchaseItemResponseDto> items;
}
