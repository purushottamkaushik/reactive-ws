package com.mamta.reactivesg.presentation;

import com.mamta.reactivesg.model.CreateUserRequest;
import com.mamta.reactivesg.model.UserResponse;
import com.mamta.reactivesg.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;


@RequestMapping("/users")
@RestController
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public Mono<ResponseEntity<UserResponse>> createUser(@Valid @RequestBody Mono<CreateUserRequest> createUserRequest) {
//        return createUserRequest.map(user -> new UserResponse(
//                UUID.randomUUID(),
//                user.getFirstName(),
//                user.getLastName(),
//                user.getEmail()
//        )).map(usrRest ->
//                ResponseEntity.
//                        status(HttpStatus.CREATED)
//                        .location(URI.create("/users/" + usrRest.getId()))
//                        .body(usrRest));

        return userService.createUser(createUserRequest)
                .map(user ->
                        ResponseEntity.status(HttpStatus.CREATED)
                                .location(URI.create("/users/" + user.getId()))
                                .body(user));
    }

    @GetMapping("/{userId}")
    public Mono<UserResponse> getUser(@PathVariable("userId") UUID userId) {
       return Mono.just(new UserResponse(
               userId,
               "Purushottam",
               "Kaushik",
               "pkaushik@gmail.com"));
    }


    @GetMapping()
    public Flux<UserResponse> getUsers(
            @RequestParam(value = "offset" ,defaultValue = "0") int offset,
            @RequestParam(value = "limit" ,defaultValue = "50") int limit
    ) {
        return Flux.just(
                new UserResponse(UUID.randomUUID(),"pu","ka","p@gmail.com"),
                new UserResponse(UUID.randomUUID(),"pu","ka","p@gmail.com"),
                new UserResponse(UUID.randomUUID(),"pu","ka","p@gmail.com")
        );
    }
}
