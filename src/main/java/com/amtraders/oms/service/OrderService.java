package com.amtraders.oms.service;

import com.amtraders.oms.dto.request.OrderRequestDto;
import com.amtraders.oms.dto.response.OrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getAllOrders();

    Optional<OrderResponseDto> getOrderById(Long id);

    OrderResponseDto updateOrder(Long id, OrderRequestDto orderRequestDto);

    void deleteOrder(Long id);
}
