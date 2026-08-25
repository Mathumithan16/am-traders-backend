package com.amtraders.oms.service;

import com.amtraders.oms.dto.request.PaymentRequestDto;
import com.amtraders.oms.dto.response.PaymentResponseDto;

import java.util.List;

public interface PaymentService {
    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);
    List<PaymentResponseDto> getAllPayments();
    PaymentResponseDto getPaymentById(Long id);
}
