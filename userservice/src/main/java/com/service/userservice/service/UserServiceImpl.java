package com.service.userservice.service;

import com.service.userservice.UserRepository;
import com.service.userservice.dto.UserDto;
import com.service.userservice.jpa.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    //해당 BCryptPasswordEncoder는 서버실행시 생성된 Bean이 따로없어서 서버실행 클래스파일에서 Bean생성후 @Autowired 해야한다.


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
}
