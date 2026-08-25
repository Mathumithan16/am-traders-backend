package com.amtraders.oms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private BigDecimal totalAmount;
    private String orderStatus;
    private Long customerId;
    private List<OrderItemRequestDto> items;
}
