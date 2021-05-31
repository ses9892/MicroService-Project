package com.service.order.controller;

import com.service.order.dto.OrderDto;
import com.service.order.jpa.OrderEntity;
import com.service.order.service.OrderSerivce;
import com.service.order.vo.RequestOrder;
import com.service.order.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/order-service/")
public class OrderController {

    @Autowired
    Environment env;

    @Autowired
    OrderSerivce orderSerivce;

    @PostMapping(value = "/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder orderDetails){
        OrderDto orderDto = new ModelMapper().map(orderDetails,OrderDto.class);
        orderDto.setUserid(userId);
        OrderDto createOrder = orderSerivce.createOder(orderDto);
        ResponseOrder responseOrder = new ModelMapper().map(createOrder,ResponseOrder.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseOrder);

    }
    @PostMapping(value = "/check")
    public RequestOrder check(@RequestBody RequestOrder orderDetails){
        return orderDetails;
    }

    @GetMapping(value = "/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId ){
        Iterable<OrderEntity> orderEntities =orderSerivce.getOrderByUserId(userId);
        List<ResponseOrder> responseOrders = new ArrayList<>();
        orderEntities.forEach(v->{
            responseOrders.add(new ModelMapper().map(v,ResponseOrder.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(responseOrders);
    }

}
