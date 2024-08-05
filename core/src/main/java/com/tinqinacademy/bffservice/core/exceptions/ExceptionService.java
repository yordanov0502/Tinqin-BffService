package com.tinqinacademy.bffservice.core.exceptions;


import com.tinqinacademy.bffservice.api.exceptions.Errors;

public interface ExceptionService {
    Errors handle(Throwable throwable);
}
