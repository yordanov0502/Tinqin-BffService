package com.tinqinacademy.bffservice.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.commentsservice.restexport.CommentsRestExport;
import com.tinqinacademy.hotel.restexport.HotelRestExport;
import feign.Contract;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RestExportFeignClientFactory {

    private final ApplicationContext applicationContext;

    @Bean
    public CommentsRestExport buildCommentsFeignClient() {
        return Feign.builder()
                .contract(new Contract.Default())
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(applicationContext.getBean(ObjectMapper.class)))
                .decoder(new JacksonDecoder(applicationContext.getBean(ObjectMapper.class)))
                .target(CommentsRestExport.class,"http://localhost:8081");
    }

    @Bean
    public HotelRestExport buildHotelFeignClient() {
        return Feign.builder()
                .contract(new Contract.Default())
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(applicationContext.getBean(ObjectMapper.class)))
                .decoder(new JacksonDecoder(applicationContext.getBean(ObjectMapper.class)))
                .target(HotelRestExport.class,"http://localhost:8080");
    }

}