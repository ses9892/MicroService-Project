package com.service.userservice.dto;

import lombok.Data;
import java.util.Date;
// DB로 변환 하기위해 RequestUser 객체가 mapper로 인해 UserDto 로 변환된다.
// 후에 UserService 메소드 createUser에 인수로 들어가는 클래스
@Data
public class UserDto{
    private String email;
    private  String pwd;
    private  String name;
    // 여기까지 받아야할 데이터
    private  String userId;
    // 무작위 코드
    private Date createdAt; //가입날짜

    private  String encryptedPassword;  //암호화된 pw
    }

