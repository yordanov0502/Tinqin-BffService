package com.tinqinacademy.bffservice.rest.security;

import com.tinqinacademy.bffservice.api.RestApiRoutes;
import com.tinqinacademy.bffservice.api.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    @Bean
    UserDetailsService emptyDetailsService() {
        return username -> { throw new NotFoundException("No local users, only JWT tokens allowed."); };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        secureAdminEndpoints(http);
        secureUserEndpoints(http);
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        http.sessionManagement((sessionManagement)-> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    private void secureAdminEndpoints(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, RestApiRoutes.REGISTER_VISITOR).hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, RestApiRoutes.GET_VISITORS).hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, RestApiRoutes.CREATE_ROOM).hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, RestApiRoutes.UPDATE_ROOM).hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, RestApiRoutes.UPDATE_ROOM_PARTIALLY).hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, RestApiRoutes.DELETE_ROOM).hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, RestApiRoutes.ADMIN_EDIT_COMMENT_FOR_ROOM).hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, RestApiRoutes.ADMIN_DELETE_COMMENT_FOR_ROOM).hasRole("ADMIN")
        );
    }

    private void secureUserEndpoints(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, RestApiRoutes.BOOK_ROOM).hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, RestApiRoutes.UNBOOK_ROOM).hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, RestApiRoutes.ADD_COMMENT_FOR_ROOM).hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, RestApiRoutes.EDIT_COMMENT_FOR_ROOM).hasAnyRole("USER", "ADMIN")
        );
    }
}