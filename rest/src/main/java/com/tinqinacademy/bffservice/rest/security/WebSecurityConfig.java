package com.tinqinacademy.bffservice.rest.security;

import com.tinqinacademy.bffservice.api.RestApiRoutes;
import com.tinqinacademy.bffservice.api.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthFilter jwtAuthFilter;

    private final String[] ADMIN_URLS = {
            RestApiRoutes.REGISTER_VISITOR,
            RestApiRoutes.GET_VISITORS,
            RestApiRoutes.CREATE_ROOM,
            RestApiRoutes.UPDATE_ROOM,
            RestApiRoutes.UPDATE_ROOM_PARTIALLY,
            RestApiRoutes.DELETE_ROOM,
            RestApiRoutes.ADMIN_EDIT_COMMENT_FOR_ROOM,
            RestApiRoutes.ADMIN_DELETE_COMMENT_FOR_ROOM
    };
    private final String[] USER_URLS = {
            RestApiRoutes.BOOK_ROOM,
            RestApiRoutes.UNBOOK_ROOM,
            RestApiRoutes.ADD_COMMENT_FOR_ROOM,
            RestApiRoutes.EDIT_COMMENT_FOR_ROOM
    };

    @Bean
    UserDetailsService emptyDetailsService() {
        return username -> { throw new NotFoundException("No local users, only JWT tokens allowed."); };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(ADMIN_URLS).hasRole("ADMIN"));
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(USER_URLS).hasAnyRole("USER", "ADMIN"));
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        http.sessionManagement((sessionManagement)-> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}