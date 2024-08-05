package com.tinqinacademy.bffservice.rest.controllers;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import com.tinqinacademy.bffservice.api.error.Errors;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public abstract class BaseController {

    protected <O extends OperationOutput> ResponseEntity<?> mapToResponseEntity(Either<Errors, O> either, HttpStatus httpStatus) {

        return either.isRight()
                ? new ResponseEntity<>(either.get(), httpStatus)
                : new ResponseEntity<>(either.getLeft().getErrorList(), either.getLeft().getHttpStatus());
    }
}
