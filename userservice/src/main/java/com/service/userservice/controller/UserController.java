package com.service.userservice.controller;

import com.service.userservice.dto.UserDto;
import com.service.userservice.service.UserServiceImpl;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import service.vo.Greeting;
import service.vo.RequestUser;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    private final Environment env ;
    @Autowired
    private UserServiceImpl userService;
    private Greeting greeting;

    public UserController(Environment env) {
        this.env = env;
    }
    @GetMapping(value = "/health_check") //서버 상태 체크
    public String status(){
        return "Good!";
    }
    @GetMapping(value = "/welcome") //서버 상태 체크
    public String welcome(){
        return env.getProperty("greeting.message");
    }

    //클라이언트에게 JSON 타입으로 요청을 받게되며 DB에 유저정보를 저장하는 가입 메소드 (POST)
    @PostMapping(value = "/users")
    public String createUser(@Valid @RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto=mapper.map(user, UserDto.class);
        userService.creatUser(userDto);
        return "Create user successful!";
    }
}
