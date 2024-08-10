package com.tinqinacademy.bffservice.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.authenticationservice.restexport.AuthenticationRestExport;
import com.tinqinacademy.commentsservice.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RestExportFeignClientFactory {

    @Value("${env.HOTEL_SERVICE_URL}")
    private String HOTEL_SERVICE_URL;
    @Value("${env.COMMENTS_SERVICE_URL}")
    private String COMMENTS_SERVICE_URL;
    @Value("${env.AUTHENTICATION_SERVICE_URL}")
    private String AUTHENTICATION_SERVICE_URL;
    private final ApplicationContext applicationContext;

    @Bean
    public HotelRestExport buildHotelFeignClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(applicationContext.getBean(ObjectMapper.class)))
                .decoder(new JacksonDecoder(applicationContext.getBean(ObjectMapper.class)))
                .target(HotelRestExport.class,HOTEL_SERVICE_URL);
    }

    @Bean
    public CommentsRestExport buildCommentsFeignClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(applicationContext.getBean(ObjectMapper.class)))
                .decoder(new JacksonDecoder(applicationContext.getBean(ObjectMapper.class)))
                .target(CommentsRestExport.class,COMMENTS_SERVICE_URL);
    }

    @Bean
    public AuthenticationRestExport buildAuthenticationFeignClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(applicationContext.getBean(ObjectMapper.class)))
                .decoder(new JacksonDecoder(applicationContext.getBean(ObjectMapper.class)))
                .target(AuthenticationRestExport.class,AUTHENTICATION_SERVICE_URL);
    }

}