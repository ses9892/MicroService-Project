package com.service.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.userservice.dto.UserDto;
import com.service.userservice.service.UserService;
import com.service.userservice.service.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import service.vo.RequestLogin;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

//로그인 시도 , 성공후 처리하는 필터가 들어있는 클래스
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment env;

    public AuthenticationFilter(UserService userService,
                                Environment env,
                                AuthenticationManager authenticationManager) {
        super((AuthenticationManager) authenticationManager);
        this.userService = userService;
        this.env = env;
    }

                                            //로그인 시도시 Security에서 처리하는 필터
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
            //요청받은 JSON을 Object 타입으로 바꾸어주는  ObjectMapper() 객체의 매소드 readValue() 메소드
            //Object에서 RequestLogin class로 변경

            //RequestLogin 객체의 이메일,비밀번호를 new UsernamePasswordAuthenticationToken()으로 변경하여
            //인증처리매니저 getAuthenticationManager()를 사용하여 인증을 처리한다.
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getPwd(),new ArrayList<>()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    //로그인 성공시 Security 에서 처리하는 필터
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.info("로그인성공!");
        log.info("인증완료된 ID : "+((User)authResult.getPrincipal()).getUsername());
        String userName = ((User)authResult.getPrincipal()).getUsername();
        UserDto userDto = userService.getUserDetailsByEmail(userName);
        log.info(String.valueOf(userDto));
        //토큰생성
        String token = Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(env.getProperty("token.expiration_time"))))
                // 토큰 만료시간 : 현재시간 + 10일후
                .signWith(SignatureAlgorithm.HS512,env.getProperty("token.secret")).compact();
                // 토큰 생성시 보안키
        
        //토큰 Header 전달 + userId 전달
        response.addHeader("token",token);
        response.addHeader("userId",userDto.getUserId());

//        authResult (인증완료) 에서  .getPrincipal() 인증된것을 가지고온다.
//         (User) security.core.userdetails 객체로 변환시켜주면 User에 저장되있는 이름을 가져올수 잇다.
    }

}
