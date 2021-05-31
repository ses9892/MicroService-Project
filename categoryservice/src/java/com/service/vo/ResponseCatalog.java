package com.service.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {
    private String itemId;
    private String itemName;
    private int price;
    private int totalPrice;
    private int EA;
}
