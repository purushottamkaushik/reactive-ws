package com.mamta.reactivesg.service;

import com.mamta.reactivesg.data.UserEntity;
import com.mamta.reactivesg.data.UserRepository;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final ReactiveAuthenticationManager reactiveAuthenticationManager;

    private final UserRepository userRepository;

    public AuthenticationServiceImpl(ReactiveAuthenticationManager reactiveAuthenticationManager,
                                     UserRepository userRepository) {
        this.reactiveAuthenticationManager = reactiveAuthenticationManager;
        this.userRepository = userRepository;
    }


    @Override
    public Mono<Map<String,String>> authenticate(String username, String password) {
        return reactiveAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password))
                .then(getUserEntity(username))
                .map(userEntity -> createUserResponse(userEntity));

    }


    private Mono<UserEntity> getUserEntity(String username) {
        return userRepository.findByEmail(username);
    }

    private Map<String,String> createUserResponse(UserEntity userEntity){
        Map<String,String> response = new HashMap<>();
        response.put("userId",userEntity.getId().toString());
        response.put("token","JTTTZTT");
        return response;

    }
}
