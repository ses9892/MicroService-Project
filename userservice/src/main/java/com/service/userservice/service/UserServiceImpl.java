package com.service.userservice.service;

import com.service.userservice.UserRepository;
import com.service.userservice.dto.UserDto;
import com.service.userservice.jpa.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto creatUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //MatchingStrategies.STRICT mapper 환경설정 전략이며 , 딱맞아떨어지기전까진 변환 할 수 없다라는뜻 궂이?
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        //Dto -> Entity 변환
        userEntity.setEncryptedPwd("encrypted_password");
        userRepository.save(userEntity);
        return null;
    }
}
