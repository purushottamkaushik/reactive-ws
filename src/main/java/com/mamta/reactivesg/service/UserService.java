package com.mamta.reactivesg.service;

import com.mamta.reactivesg.model.CreateUserRequest;
import com.mamta.reactivesg.model.UserResponse;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserResponse> createUser(Mono<CreateUserRequest> createUserRequestMono);

    Mono<UserResponse> getUserId(Long id);

}
