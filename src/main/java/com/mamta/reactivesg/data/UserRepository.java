package com.mamta.reactivesg.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {

    Flux<UserEntity> findAllBy(Pageable pageable);

    Mono<UserEntity> findByEmail(String username);
}
