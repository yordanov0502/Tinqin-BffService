package com.tinqinacademy.bffservice.core.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bffservice.api.exceptions.Error;
import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.api.exceptions.custom.CustomException;
import com.tinqinacademy.bffservice.api.exceptions.custom.ViolationsException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Component
@RequiredArgsConstructor
public class ExceptionHandler implements ExceptionService {

    private final ObjectMapper objectMapper;

    @Override
    public Errors handle(Throwable throwable) {
        return Match(throwable).of(
                Case($(instanceOf(CustomException.class)), this::handleCustomException),
                Case($(instanceOf(ViolationsException.class)), this::handleViolationsException),
                Case($(instanceOf(FeignException.class)), this::handleFeignException),
                Case($(), this::handleDefaultException)
        );
    }

    private Errors handleCustomException(CustomException ex) {
        return Errors.builder()
                .errorList(List.of(Error.builder().errMsg(ex.getMessage()).build()))
                .httpStatus(ex.getHttpStatus())
                .build();
    }

    private Errors handleViolationsException(ViolationsException ex) {
        return Errors.builder()
                .errorList(ex.getErrorList())
                .httpStatus(ex.getHttpStatus())
                .build();
    }

    private Errors handleFeignException(FeignException ex) {
        String defaultErrorMessage = "Unknown error occurred";
        List<Error> errorList = new ArrayList<>();

        try {
            String responseBody = ex.contentUTF8();

            List<Map<String, String>> responseList = objectMapper.readValue(responseBody, List.class);

            if (!responseList.isEmpty()) {
                Map<String, String> firstError = responseList.get(0);

                if (firstError.containsKey("field") && firstError.containsKey("errMsg")) {
                    for (Map<String, String> errorMap : responseList) {
                        String field = errorMap.get("field");
                        String errMsg = errorMap.get("errMsg");
                        errorList.add(Error.builder()
                                .field(field)
                                .errMsg(errMsg)
                                .build());
                    }
                } else if (firstError.containsKey("errMsg")) {
                    String errMsg = firstError.get("errMsg");
                    errorList.add(Error.builder().errMsg(errMsg).build());
                }
            } else {
                errorList.add(Error.builder().errMsg(defaultErrorMessage).build());
            }

        } catch (IOException e) {
            errorList.add(Error.builder().errMsg(ex.getMessage()).build());
        }

        return Errors.builder()
                .errorList(errorList)
                .httpStatus(HttpStatus.valueOf(ex.status()))
                .build();
    }

    private Errors handleDefaultException(Throwable throwable) {
        return Match(throwable).of(

                Case($(instanceOf(UnsupportedOperationException.class)),
                        ex -> Errors.builder()
                                .errorList(List.of(Error.builder().errMsg(ex.getMessage()).build()))
                                .httpStatus(HttpStatus.BAD_REQUEST) //! NOT_IMPLEMENTED
                                .build()),

                Case($(),
                        ex -> Errors.builder()
                                .errorList(List.of(Error.builder().errMsg(ex.getMessage()).build()))
                                .httpStatus(HttpStatus.BAD_REQUEST) //! INTERNAL_SERVER_ERROR
                                .build())
        );

    }
}
