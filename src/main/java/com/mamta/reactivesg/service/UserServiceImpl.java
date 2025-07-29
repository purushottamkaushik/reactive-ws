package com.mamta.reactivesg.service;

import com.mamta.reactivesg.data.UserEntity;
import com.mamta.reactivesg.data.UserRepository;
import com.mamta.reactivesg.model.CreateUserRequest;
import com.mamta.reactivesg.model.UserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UserResponse> createUser(Mono<CreateUserRequest> createUserRequestMono) {
        // Create userEntity class
        return createUserRequestMono
                .flatMap(this::convertToEntity)
                .flatMap(userRepository::save)
                .mapNotNull(this::convertToResponse);


    }

    @Override
    public Mono<UserResponse> getUserId(Long id) {
        return userRepository.findById(id)
                .mapNotNull(this::convertToResponse);
    }

    @Override
    public Flux<UserResponse> getPaginatedData(int page, int size) {
        if (page == 1) {
            page = page - 1;
        }
        Pageable pageable = PageRequest.of(page, size);
        return this.userRepository.findAllBy(pageable)
                .map(userEntity -> convertToResponse(userEntity))
                ;
    }

    private Mono<UserEntity> convertToEntity(CreateUserRequest createUserRequest) {
       // When compute intensive task is there use Mono.fromCallable()
       return Mono.fromCallable(()->{
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(createUserRequest, userEntity);
            userEntity.setPassword(passwordEncoder.encode(createUserRequest.getPassword())); // compute intensive task
            return userEntity;
        });

    }
    private UserResponse convertToResponse(UserEntity userEntity) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(userEntity, response);
        return response;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
                .map(userEntity ->
                        User.withUsername(userEntity.getEmail())
                            .password(userEntity.getPassword())
                            .authorities(new ArrayList<>())
                            .build()
                );
    }
}
