package com.tinqinacademy.bffservice.api.operations.hotelservice.system.getvisitors;

import com.tinqinacademy.bffservice.api.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetVisitorsBffInput implements OperationInput {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String idCardNumber;
    private LocalDate idCardValidity;
    private String idCardIssueAuthority;
    private LocalDate idCardIssueDate;
    private String roomNumber;

}
