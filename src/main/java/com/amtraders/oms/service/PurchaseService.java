package com.amtraders.oms.service;

import com.amtraders.oms.dto.request.PurchaseRequestDto;
import com.amtraders.oms.dto.request.PurchaseRequestDto;
import com.amtraders.oms.dto.response.PurchaseResponseDto;

import java.util.List;

public interface PurchaseService {
    PurchaseResponseDto createPurchase(PurchaseRequestDto purchaseRequestDto);
    List<PurchaseResponseDto> getAllPurchases();
    PurchaseResponseDto getPurchaseById(Long id);
}
