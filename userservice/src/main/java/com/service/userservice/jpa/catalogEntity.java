package com.service.userservice.jpa;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "catalog")
public class catalogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(nullable = false,length = 120,unique = true)
    String itemId;

    @Column(nullable = false)
    String itemName;

    @Column(nullable = false)
    int EA;

    @Column(nullable = false)
    int price;
}
