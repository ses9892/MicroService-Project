package com.service.order.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestOrder {
    private String productid;
    private int qty;
    private int price;

}
