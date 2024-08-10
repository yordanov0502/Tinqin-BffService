package com.tinqinacademy.bffservice.rest.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class CustomAuthToken extends AbstractAuthenticationToken {

   private final UserDetailsModel userDetailsModel;

    public CustomAuthToken(UserDetailsModel userDetailsModel) {
        super(Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+userDetailsModel.getRole().toUpperCase())));
        this.userDetailsModel = userDetailsModel;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return userDetailsModel;
    }
}
