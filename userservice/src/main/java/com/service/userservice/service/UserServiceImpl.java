package com.service.userservice.service;

import com.service.userservice.UserRepository;
import com.service.userservice.dto.UserDto;
import com.service.userservice.jpa.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.vo.RequestUpdateUser;
import service.vo.ResponseOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements  UserService{
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Environment env;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    //해당 BCryptPasswordEncoder는 서버실행시 생성된 Bean이 따로없어서 서버실행 클래스파일에서 Bean생성후 @Autowired 해야한다.

    //UserService -> UserDetailsService 의 메소드 재정의의
   @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
       UserEntity userEntity = userRepository.findByEmail(userName);

       if(userEntity==null){
             throw  new UsernameNotFoundException("userName is Not");
       }
        return new User(userEntity.getEmail(),userEntity.getEncryptedPwd(),true,
                true,true,true
                ,new ArrayList<>());
    }

    @Override
    public UserDto creatUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //MatchingStrategies.STRICT mapper 환경설정 전략이며 , 딱맞아떨어지기전까진 변환 할 수 없다라는뜻 궂이?
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        //Dto -> Entity 변환
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        //Dto 에있는 pw 가암호화되어 DB에저장.
        userRepository.save(userEntity);
        userDto = mapper.map(userEntity,UserDto.class);
        return userDto;
    }
    //유저 1건을 조회할 메소드
    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity==null){
            throw new UsernameNotFoundException("User Name Not found");
        }
        UserDto userDto = new ModelMapper().map(userEntity,UserDto.class);
        List<ResponseOrder> orderList = new ArrayList<>();
        String orderUrl = String.format(env.getProperty("order_serivce.url"),userId);
        ResponseEntity<List<ResponseOrder>> orderListResponse =restTemplate.exchange(orderUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ResponseOrder>>() {
                });
        // ResponseEntity의 메소드 getBody() : ResponseEntity의 Type으로 변환해서 리턴해준다.
        orderList = orderListResponse.getBody();
        userDto.setOrders(orderList);
        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String userName) {
        UserEntity userEntity =userRepository.findByEmail(userName);
        if(userEntity==null){
            throw new UsernameNotFoundException("userName is Not Found!!");
        }
        return new ModelMapper().map(userEntity,UserDto.class);
    }

    @Override
    public UserDto updateUserByUserId(String userId, RequestUpdateUser requestUpdateUser) {
       UserEntity userEntity = userRepository.findByUserId(userId);
       if(userEntity==null){
           throw new UsernameNotFoundException("해당 유저는 존재 하지 않습니다.");
       }
// 0000 ,0000  = ok
        // DB에는 인코딩되어있는 패스워드 자체로 되어있때문
       if(!passwordEncoder.matches(requestUpdateUser.getPwd(),userEntity.getEncryptedPwd())){
           throw new UsernameNotFoundException("비밀번호가 일치 하지 않습니다");
       }
       userEntity.setEncryptedPwd(passwordEncoder.encode(requestUpdateUser.getUpdatePwd()));

       userRepository.save(userEntity);
       UserDto userDto = new ModelMapper().map(userEntity,UserDto.class);
        return userDto;
    }

}
