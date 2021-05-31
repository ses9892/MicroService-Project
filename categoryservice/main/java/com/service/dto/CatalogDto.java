package com.service.dto;

import lombok.Data;

@Data
public class CatalogDto {
    private String productId;
    private Integer qty;
    private int unitPrice;
    private int totalPrice;

    private String orderId;
    private String userId;

}
