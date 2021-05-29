package com.service.userservice.controller;

import com.service.userservice.dto.UserDto;
import com.service.userservice.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.vo.Greeting;
import service.vo.RequestUser;
import service.vo.ResponseUser;

import javax.validation.Valid;
import java.net.URI;

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
    public ResponseEntity<ResponseUser> createUser(@Valid @RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto=mapper.map(user, UserDto.class);
        userDto = userService.creatUser(userDto);

//        해당 URI를 같이 출력 : Get타입으로 UserId를 파라미터로 유저를 바로 조회할 수 있는 URI 까지
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}").buildAndExpand(userDto.getUserId()).toUri();
//        URI 와 201Created 응답
        ResponseUser responseUser = mapper.map(userDto,ResponseUser.class);
        return ResponseEntity.created(location).body(responseUser);
                //status(HttpStatus.CREATED).build();
    }
}
