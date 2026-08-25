package com.amtraders.oms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String paymentMethod; // CASH, BANK

    private LocalDateTime paymentDate;

    private String status; // PENDING, PAID

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}