package com.service.order.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false,length = 120)
    private String productid;
    @Column(nullable = false)
    private Integer qty;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer totalprice;
    @Column(nullable = false)
    private String userid;
    @Column(nullable = false,unique = true)
    private String orderid;
}
