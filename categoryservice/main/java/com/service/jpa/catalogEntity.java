package com.service.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "catalog")
public class catalogEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false,length = 120,unique = true)
    String itemId;

    @Column(nullable = false)
    String itemName;

    @Column(nullable = false)
    int EA;

    @Column(nullable = false)
    int price;

    @Column(nullable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    Date createdAt;
}
