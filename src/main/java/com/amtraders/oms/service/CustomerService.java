package com.amtraders.oms.service;

import com.amtraders.oms.dto.request.CustomerRequestDto;
import com.amtraders.oms.dto.response.CustomerResponseDto;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto);
    List<CustomerResponseDto> getAllCustomers();
    CustomerResponseDto getCustomerById(Long id);
}
