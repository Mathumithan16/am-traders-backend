package com.amtraders.oms.controller;

import com.amtraders.oms.dto.request.SupplierRequestDto;
import com.amtraders.oms.dto.response.SupplierResponseDto;
import com.amtraders.oms.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
@CrossOrigin(origins = "*")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public SupplierResponseDto createSupplier(@RequestBody SupplierRequestDto supplierRequestDto) {
        return supplierService.createSupplier(supplierRequestDto);
    }

    @GetMapping
    public List<SupplierResponseDto> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }
}
