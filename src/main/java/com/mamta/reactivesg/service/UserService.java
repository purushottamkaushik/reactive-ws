package com.mamta.reactivesg.service;

import com.mamta.reactivesg.model.CreateUserRequest;
import com.mamta.reactivesg.model.UserResponse;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {

    Mono<UserResponse> createUser(Mono<CreateUserRequest> createUserRequestMono);

    Mono<UserResponse> getUserId(Long id);

    Flux<UserResponse> getPaginatedData(int page, int size);

}
