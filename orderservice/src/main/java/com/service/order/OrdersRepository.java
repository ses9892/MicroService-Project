package com.service.order;

import com.service.order.jpa.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<OrderEntity,Long> {
    OrderEntity findByOrderid(String orderId);
    Iterable<OrderEntity> findByUserid(String userId);
}
