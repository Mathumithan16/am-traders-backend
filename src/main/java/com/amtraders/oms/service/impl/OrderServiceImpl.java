package com.amtraders.oms.service.impl;

import com.amtraders.oms.dto.request.OrderRequestDto;
import com.amtraders.oms.dto.response.OrderItemResponseDto;
import com.amtraders.oms.dto.response.OrderResponseDto;
import com.amtraders.oms.entity.Customer;
import com.amtraders.oms.entity.Order;
import com.amtraders.oms.entity.OrderItem;
import com.amtraders.oms.entity.Product;
import com.amtraders.oms.enums.OrderStatus;
import com.amtraders.oms.exception.ResourceNotFoundException;
import com.amtraders.oms.repository.CustomerRepository;
import com.amtraders.oms.repository.OrderRepository;
import com.amtraders.oms.repository.ProductRepository;
import com.amtraders.oms.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(orderRequestDto.getTotalAmount());
        order.setOrderStatus(OrderStatus.valueOf(orderRequestDto.getOrderStatus()));

        if (orderRequestDto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(orderRequestDto.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + orderRequestDto.getCustomerId()));
            order.setCustomer(customer);
        }

        List<OrderItem> items = new ArrayList<>();
        if (orderRequestDto.getItems() != null) {
            orderRequestDto.getItems().forEach(itemDto -> {
                OrderItem item = new OrderItem();
                item.setQuantity(itemDto.getQuantity());
                item.setSellingPrice(itemDto.getSellingPrice());
                item.setSubtotal(itemDto.getSubtotal());
                item.setOrder(order);

                if (itemDto.getProductId() != null) {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDto.getProductId()));
                    item.setProduct(product);
                    
                    // Reduce product stock
                    product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                    productRepository.save(product);
                }
                items.add(item);
            });
        }
        order.setItems(items);

        Order savedOrder = orderRepository.save(order);
        return mapToResponseDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderResponseDto> getOrderById(Long id) {
        return orderRepository.findById(id).map(this::mapToResponseDto);
    }

    @Override
    public OrderResponseDto updateOrder(Long id, OrderRequestDto orderRequestDto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        existingOrder.setOrderStatus(OrderStatus.valueOf(orderRequestDto.getOrderStatus()));
        // Note: Full update of items or amounts requires more complex logic. Keeping it simple here.

        Order updatedOrder = orderRepository.save(existingOrder);
        return mapToResponseDto(updatedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    private OrderResponseDto mapToResponseDto(Order order) {
        List<OrderItemResponseDto> itemDtos = order.getItems() != null ?
                order.getItems().stream().map(item -> new OrderItemResponseDto(
                        item.getId(),
                        item.getQuantity(),
                        item.getSellingPrice(),
                        item.getSubtotal(),
                        item.getProduct() != null ? item.getProduct().getId() : null
                )).collect(Collectors.toList()) : new ArrayList<>();

        return new OrderResponseDto(
                order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getOrderStatus().name(),
                order.getCustomer() != null ? order.getCustomer().getId() : null,
                itemDtos
        );
    }
}