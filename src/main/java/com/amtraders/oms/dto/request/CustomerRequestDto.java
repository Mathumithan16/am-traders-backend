package com.amtraders.oms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {
    private String shopName;
    private String ownerName;
    private String address;
    private Long userId;
}
