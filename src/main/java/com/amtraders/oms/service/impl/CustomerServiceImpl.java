package com.amtraders.oms.service.impl;

import com.amtraders.oms.dto.request.CustomerRequestDto;
import com.amtraders.oms.dto.response.CustomerResponseDto;
import com.amtraders.oms.entity.Customer;
import com.amtraders.oms.entity.User;
import com.amtraders.oms.exception.ResourceNotFoundException;
import com.amtraders.oms.repository.CustomerRepository;
import com.amtraders.oms.repository.UserRepository;
import com.amtraders.oms.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = new Customer();
        customer.setShopName(customerRequestDto.getShopName());
        customer.setOwnerName(customerRequestDto.getOwnerName());
        customer.setAddress(customerRequestDto.getAddress());

        if (customerRequestDto.getUserId() != null) {
            User user = userRepository.findById(customerRequestDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + customerRequestDto.getUserId()));
            customer.setUser(user);
        }

        Customer saved = customerRepository.save(customer);
        return mapToResponseDto(saved);
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return mapToResponseDto(customer);
    }

    private CustomerResponseDto mapToResponseDto(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                customer.getShopName(),
                customer.getOwnerName(),
                customer.getAddress(),
                customer.getUser() != null ? customer.getUser().getId() : null
        );
    }
}
