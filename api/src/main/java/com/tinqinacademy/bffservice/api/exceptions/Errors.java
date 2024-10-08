package com.tinqinacademy.bffservice.api.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Errors {
    List<Error> errorList;
    private HttpStatus httpStatus;
}