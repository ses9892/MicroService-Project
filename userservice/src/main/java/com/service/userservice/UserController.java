package com.service.userservice;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class UserController {

    private final Environment env ;

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
}
