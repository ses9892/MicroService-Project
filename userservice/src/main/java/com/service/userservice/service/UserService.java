package com.service.userservice.service;

import com.service.userservice.dto.UserDto;
import com.service.userservice.jpa.UserEntity;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import service.vo.RequestUpdateUser;

import java.util.ArrayList;

// 재정의할 추상메소드를 만들어놓는 인터페이스
public interface UserService extends UserDetailsService {
    UserDto creatUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserAll();
    UserDto getUserDetailsByEmail(String userName);

    UserDto updateUserByUserId(String userId, RequestUpdateUser requestUpdateUser);
}
