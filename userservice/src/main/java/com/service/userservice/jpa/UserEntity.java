package com.service.userservice.jpa;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(nullable = false,length = 50,unique = true)
    String email;
    @Column(nullable = false,length = 50)
    String name;
    @Column(nullable = false,unique = true)
    String userId;
    @Column(nullable = false,unique = true)
    String encryptedPwd;

}
