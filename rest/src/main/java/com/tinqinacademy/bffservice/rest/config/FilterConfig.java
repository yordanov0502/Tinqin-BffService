package com.tinqinacademy.bffservice.rest.config;

import com.tinqinacademy.bffservice.rest.filters.RequestIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<RequestIdFilter> loggingFilter(){
        FilterRegistrationBean<RequestIdFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestIdFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}
