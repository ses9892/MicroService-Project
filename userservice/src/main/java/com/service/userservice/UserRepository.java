package com.service.userservice;

import com.service.userservice.jpa.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@EnableJpaRepositories
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findByUserId(String userId);

    UserEntity findByEmail(String userName);
}
