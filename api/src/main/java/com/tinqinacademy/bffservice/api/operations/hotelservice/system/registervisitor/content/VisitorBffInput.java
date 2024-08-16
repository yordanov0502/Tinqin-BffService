package com.tinqinacademy.bffservice.api.operations.hotelservice.system.registervisitor.content;

import com.tinqinacademy.bffservice.api.validation.name.NameRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VisitorBffInput {
    @NotBlank
    @UUID
    private String roomId;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NameRegex
    private String firstName;
    @NameRegex
    private String lastName;
    @NotNull
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String idCardNumber;
    private LocalDate idCardValidity;
    private String idCardIssueAuthority;
    private LocalDate idCardIssueDate;
}
