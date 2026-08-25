package com.amtraders.oms.service;

import com.amtraders.oms.dto.request.SupplierRequestDto;
import com.amtraders.oms.dto.response.SupplierResponseDto;

import java.util.List;

public interface SupplierService {
    SupplierResponseDto createSupplier(SupplierRequestDto supplierRequestDto);
    List<SupplierResponseDto> getAllSuppliers();
    SupplierResponseDto getSupplierById(Long id);
}
