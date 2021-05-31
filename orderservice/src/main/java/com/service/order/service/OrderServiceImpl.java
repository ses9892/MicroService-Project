package com.service.order.service;

import com.service.order.OrdersRepository;
import com.service.order.dto.OrderDto;
import com.service.order.jpa.OrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderSerivce{

    @Autowired
    OrdersRepository repository;

    @Override
    public OrderDto createOder(OrderDto orderDto) {
        orderDto.setOrderid(UUID.randomUUID().toString());
        orderDto.setTotalprice(orderDto.getPrice()* orderDto.getQty());
        log.info(String.valueOf(orderDto));
        OrderEntity orderEntity = new ModelMapper().map(orderDto,OrderEntity.class);

        repository.save(orderEntity);
        OrderDto dto = new ModelMapper().map(orderEntity,OrderDto.class);
        return dto;
    }

    //1개검색메소드
    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = repository.findByOrderid(orderId);
        OrderDto orderDto = new ModelMapper().map(orderEntity,OrderDto.class);

        return orderDto;
    }
    //ID검색 ID가 쓴것가지고오기
    @Override
    public Iterable<OrderEntity> getOrderByUserId(String userId) {
        Iterable<OrderEntity> orderEntities = repository.findByUserid(userId);
        log.info(String.valueOf(orderEntities));
        return orderEntities;
    }

}
