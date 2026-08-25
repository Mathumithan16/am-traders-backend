package com.amtraders.oms.service.impl;

import com.amtraders.oms.dto.request.ProductRequestDto;
import com.amtraders.oms.dto.response.ProductResponseDto;
import com.amtraders.oms.entity.Category;
import com.amtraders.oms.entity.Product;
import com.amtraders.oms.entity.Supplier;
import com.amtraders.oms.exception.ResourceNotFoundException;
import com.amtraders.oms.repository.CategoryRepository;
import com.amtraders.oms.repository.ProductRepository;
import com.amtraders.oms.repository.SupplierRepository;
import com.amtraders.oms.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, SupplierRepository supplierRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        mapToEntity(productRequestDto, product);
        Product savedProduct = productRepository.save(product);
        return mapToResponseDto(savedProduct);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductResponseDto> getProductById(Long id) {
        return productRepository.findById(id).map(this::mapToResponseDto);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        mapToEntity(productRequestDto, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return mapToResponseDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private void mapToEntity(ProductRequestDto dto, Product product) {
        product.setName(dto.getName());
        product.setSize(dto.getSize());
        product.setBuyPrice(dto.getBuyPrice());
        product.setSellingPrice(dto.getSellingPrice());
        product.setStockQuantity(dto.getStockQuantity());

        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + dto.getSupplierId()));
            product.setSupplier(supplier);
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
            product.setCategory(category);
        }
    }

    private ProductResponseDto mapToResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getSize(),
                product.getBuyPrice(),
                product.getSellingPrice(),
                product.getStockQuantity(),
                product.getSupplier() != null ? product.getSupplier().getId() : null,
                product.getCategory() != null ? product.getCategory().getId() : null
        );
    }
}