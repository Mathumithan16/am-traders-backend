package com.amtraders.oms.service.impl;

import com.amtraders.oms.dto.request.PurchaseRequestDto;
import com.amtraders.oms.dto.response.PurchaseItemResponseDto;
import com.amtraders.oms.dto.response.PurchaseResponseDto;
import com.amtraders.oms.entity.Product;
import com.amtraders.oms.entity.Purchase;
import com.amtraders.oms.entity.PurchaseItem;
import com.amtraders.oms.entity.Supplier;
import com.amtraders.oms.exception.ResourceNotFoundException;
import com.amtraders.oms.repository.ProductRepository;
import com.amtraders.oms.repository.PurchaseRepository;
import com.amtraders.oms.repository.SupplierRepository;
import com.amtraders.oms.service.PurchaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, SupplierRepository supplierRepository, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }

    @Override
    public PurchaseResponseDto createPurchase(PurchaseRequestDto purchaseRequestDto) {
        Purchase purchase = new Purchase();
        purchase.setTotalAmount(purchaseRequestDto.getTotalAmount());
        purchase.setPurchaseDate(LocalDateTime.now());
        
        if (purchaseRequestDto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(purchaseRequestDto.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + purchaseRequestDto.getSupplierId()));
            purchase.setSupplier(supplier);
        }

        List<PurchaseItem> items = new ArrayList<>();
        if (purchaseRequestDto.getItems() != null) {
            purchaseRequestDto.getItems().forEach(itemDto -> {
                PurchaseItem item = new PurchaseItem();
                item.setQuantity(itemDto.getQuantity());
                item.setBuyPrice(itemDto.getBuyPrice());
                item.setSubtotal(itemDto.getSubtotal());
                item.setPurchase(purchase);

                if (itemDto.getProductId() != null) {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDto.getProductId()));
                    item.setProduct(product);
                    
                    // Update product stock
                    product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                    productRepository.save(product);
                }
                items.add(item);
            });
        }
        purchase.setItems(items);

        Purchase saved = purchaseRepository.save(purchase);
        return mapToResponseDto(saved);
    }

    @Override
    public List<PurchaseResponseDto> getAllPurchases() {
        return purchaseRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseResponseDto getPurchaseById(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id: " + id));
        return mapToResponseDto(purchase);
    }

    private PurchaseResponseDto mapToResponseDto(Purchase purchase) {
        List<PurchaseItemResponseDto> itemDtos = purchase.getItems() != null ?
                purchase.getItems().stream().map(item -> new PurchaseItemResponseDto(
                        item.getId(),
                        item.getQuantity(),
                        item.getBuyPrice(),
                        item.getSubtotal(),
                        item.getProduct() != null ? item.getProduct().getId() : null
                )).collect(Collectors.toList()) : new ArrayList<>();

        return new PurchaseResponseDto(
                purchase.getId(),
                purchase.getPurchaseDate(),
                purchase.getTotalAmount(),
                purchase.getSupplier() != null ? purchase.getSupplier().getId() : null,
                itemDtos
        );
    }
}
