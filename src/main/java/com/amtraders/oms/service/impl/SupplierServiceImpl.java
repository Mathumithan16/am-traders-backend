package com.amtraders.oms.service.impl;

import com.amtraders.oms.dto.request.SupplierRequestDto;
import com.amtraders.oms.dto.response.SupplierResponseDto;
import com.amtraders.oms.entity.Supplier;
import com.amtraders.oms.exception.ResourceNotFoundException;
import com.amtraders.oms.repository.SupplierRepository;
import com.amtraders.oms.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public SupplierResponseDto createSupplier(SupplierRequestDto supplierRequestDto) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierRequestDto.getName());
        supplier.setEmail(supplierRequestDto.getEmail());
        supplier.setPhone(supplierRequestDto.getPhone());
        supplier.setAddress(supplierRequestDto.getAddress());
        Supplier saved = supplierRepository.save(supplier);
        return mapToResponseDto(saved);
    }

    @Override
    public List<SupplierResponseDto> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierResponseDto getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        return mapToResponseDto(supplier);
    }

    private SupplierResponseDto mapToResponseDto(Supplier supplier) {
        return new SupplierResponseDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getAddress()
        );
    }
}
