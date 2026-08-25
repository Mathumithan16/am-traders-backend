package com.amtraders.oms.controller;

import com.amtraders.oms.dto.request.PurchaseRequestDto;
import com.amtraders.oms.dto.response.PurchaseResponseDto;
import com.amtraders.oms.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase")
@CrossOrigin(origins = "*")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public PurchaseResponseDto createPurchase(@RequestBody PurchaseRequestDto purchaseRequestDto) {
        return purchaseService.createPurchase(purchaseRequestDto);
    }

    @GetMapping
    public List<PurchaseResponseDto> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }
}
