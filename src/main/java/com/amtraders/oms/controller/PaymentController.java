package com.amtraders.oms.controller;

import com.amtraders.oms.dto.request.PaymentRequestDto;
import com.amtraders.oms.dto.response.PaymentResponseDto;
import com.amtraders.oms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentResponseDto createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        return paymentService.createPayment(paymentRequestDto);
    }

    @GetMapping
    public List<PaymentResponseDto> getAllPayments() {
        return paymentService.getAllPayments();
    }
}
