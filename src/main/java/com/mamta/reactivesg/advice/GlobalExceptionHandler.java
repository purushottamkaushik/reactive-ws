package com.mamta.reactivesg.advice;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public Mono<ErrorResponse> handleDuplicateException(DuplicateKeyException dke){
        return Mono.just(ErrorResponse.builder(dke, HttpStatus.CONFLICT,dke.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleException(Exception e){
        return Mono.just(ErrorResponse.builder(e, HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage()).build());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ErrorResponse> handleWebExchangeBindException(WebExchangeBindException webe){
        String errorMessages = webe
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(",", " ", " "));

        return Mono.just(ErrorResponse.builder(webe, HttpStatus.BAD_REQUEST,errorMessages).build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Mono<ErrorResponse> handleBadCredentialsException(BadCredentialsException bce){
        return Mono.just(ErrorResponse.builder(bce, HttpStatus.UNAUTHORIZED,bce.getMessage()).build());
    }
}
