package com.tinqinacademy.bffservice.rest.security;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class UserContext {
    private String userId;

    void setContext(String userId){
        this.userId = userId;
    }
}