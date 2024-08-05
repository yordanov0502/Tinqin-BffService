package com.tinqinacademy.bffservice.core.exceptions;


import com.tinqinacademy.bffservice.api.error.Errors;

public interface ExceptionService {
    Errors handle(Throwable throwable);
}
