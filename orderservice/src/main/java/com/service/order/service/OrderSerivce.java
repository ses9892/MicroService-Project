package com.service.order.service;

import com.service.order.dto.OrderDto;
import com.service.order.jpa.OrderEntity;

import javax.persistence.criteria.Order;

public interface OrderSerivce {

    OrderDto createOder(OrderDto orderDto);
    //주문조회
    OrderDto getOrderByOrderId(String orderId);
    //주문자 조회
    Iterable<OrderEntity> getOrderByUserId(String userId);

}
