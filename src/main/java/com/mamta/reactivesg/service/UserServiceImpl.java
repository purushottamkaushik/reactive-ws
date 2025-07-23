package com.mamta.reactivesg.service;

import com.mamta.reactivesg.data.UserEntity;
import com.mamta.reactivesg.data.UserRepository;
import com.mamta.reactivesg.model.CreateUserRequest;
import com.mamta.reactivesg.model.UserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserResponse> createUser(Mono<CreateUserRequest> createUserRequestMono) {
        // Create userEntity class
        return createUserRequestMono.mapNotNull(this::convertToEntity)
                .flatMap(userRepository::save)
                .mapNotNull(this::convertToResponse);

    }

    private UserEntity convertToEntity(CreateUserRequest createUserRequest) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(createUserRequest, userEntity);
        return userEntity;
    }
    private UserResponse convertToResponse(UserEntity userEntity) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(userEntity, response);
        return response;
    }
}
