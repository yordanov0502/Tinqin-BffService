package com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.bookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bffservice.api.base.OperationInput;
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
public class BookRoomBffInput implements OperationInput {

    @JsonIgnore
    @UUID
    private String roomId;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @JsonIgnore
    @UUID
    private String userId;
}
