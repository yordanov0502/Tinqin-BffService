package com.tinqinacademy.bffservice.api.error;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String field;
    private String errMsg;
}
