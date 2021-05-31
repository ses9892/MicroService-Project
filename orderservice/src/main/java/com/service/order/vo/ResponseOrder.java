package com.service.order.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
@Data
public class ResponseOrder {
    private String productid;
    private int qty;
    private int price;
    private int totalprice;

    private String orderid;

}
