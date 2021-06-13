package com.service.userservice.controller;

import com.service.userservice.dto.UserDto;
import com.service.userservice.feign.OrderServiceClient;
import com.service.userservice.jpa.UserEntity;
import com.service.userservice.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.vo.Greeting;
import service.vo.RequestUpdateUser;
import service.vo.RequestUser;
import service.vo.ResponseUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/")
@Slf4j
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
        return String.format("Sever Port(s)= "+env.getProperty("local.server.port")+
                            ",with token secret= "+env.getProperty("token.secret")+
                ",with token time= "+env.getProperty("token.expiration_time"));
    }
    @GetMapping(value = "/welcome") //서버 상태 체크
    public String welcome(HttpServletRequest request) {
        log.info(request.getParameter("stack"));
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
        //클라이언트 헤더에 유저의 정보를 확인하는 헤더 URI 이 전달된다.
        //status(HttpStatus.CREATED).build();
    }
    //유저 전체조회 요청 메소드
    @GetMapping(value = "/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userService.getUserAll();
        List<ResponseUser> users = new ArrayList<>();
        for (UserEntity responseUser:userList) {
            users.add(new ModelMapper().map(responseUser,ResponseUser.class));
        }
        // HATEOAS 해도 되는구간=====
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    //개별유저 조회
    // produces 속성은 consumes와 반대로 클라이언트가 요청을 보냈을 때 특정한 데이터 타입으로 응답하겠다는 속성
    // 즉, 무조건 JSON 타입으로 응답하겠다 이말
    @GetMapping(value = "/users/{userId}" , produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseUser> getUserId(@PathVariable("userId") String userId){
        UserDto user = userService.getUserByUserId(userId);
        ResponseUser responseUser = new ModelMapper().map(user,ResponseUser.class);
        // HATEOAS 해도 되는구간=====
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }
    @PutMapping(value = "/users/{userId}")
    public ResponseEntity<ResponseUser> updateUserId(@PathVariable("userId")String userId,
                                                     @Valid @RequestBody RequestUpdateUser RequestUpdateUser){
       UserDto userDto =  userService.updateUserByUserId(userId,RequestUpdateUser);
       ResponseUser responseUser = new ModelMapper().map(userDto,ResponseUser.class);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseUser);
    }


}

