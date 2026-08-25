package com.amtraders.oms.service.impl;

import com.amtraders.oms.dto.request.PaymentRequestDto;
import com.amtraders.oms.dto.response.PaymentResponseDto;
import com.amtraders.oms.entity.Order;
import com.amtraders.oms.entity.Payment;
import com.amtraders.oms.exception.ResourceNotFoundException;
import com.amtraders.oms.repository.OrderRepository;
import com.amtraders.oms.repository.PaymentRepository;
import com.amtraders.oms.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {
        Payment payment = new Payment();
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setPaymentMethod(paymentRequestDto.getPaymentMethod());
        payment.setStatus(paymentRequestDto.getStatus());
        payment.setPaymentDate(LocalDateTime.now());

        if (paymentRequestDto.getOrderId() != null) {
            Order order = orderRepository.findById(paymentRequestDto.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + paymentRequestDto.getOrderId()));
            payment.setOrder(order);
        }

        Payment saved = paymentRepository.save(payment);
        return mapToResponseDto(saved);
    }

    @Override
    public List<PaymentResponseDto> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return mapToResponseDto(payment);
    }

    private PaymentResponseDto mapToResponseDto(Payment payment) {
        return new PaymentResponseDto(
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentDate(),
                payment.getStatus(),
                payment.getOrder() != null ? payment.getOrder().getId() : null
        );
    }
}
