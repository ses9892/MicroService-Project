package com.service.userservice.service;

import com.service.userservice.dto.UserDto;

// 재정의할 추상메소드를 만들어놓는 인터페이스
public interface UserService {
    UserDto creatUser(UserDto userDto);
}
