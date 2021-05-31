package com.service.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto implements Serializable {
    private String productid;
    private Integer qty;
    private Integer price;
    private Integer totalprice;
    private String userid;
    private String orderid;
}
