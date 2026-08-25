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
public class OrderResponseDto {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String orderStatus;
    private Long customerId;
    private List<OrderItemResponseDto> items;
}
