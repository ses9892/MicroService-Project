package com.service.userservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import service.vo.ResponseOrder;

import java.util.List;

@FeignClient(name = "order-service") //Eurek server에 등록된 order-service 의 spring.application.name
public interface OrderServiceClient {

    @GetMapping(value = "/order-service/{userId}/orders")
    List<ResponseOrder> getOrders(@PathVariable String userId);
}
