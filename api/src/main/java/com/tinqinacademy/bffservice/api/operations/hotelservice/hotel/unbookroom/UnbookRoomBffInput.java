package com.tinqinacademy.bffservice.api.operations.hotelservice.hotel.unbookroom;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bffservice.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UnbookRoomBffInput implements OperationInput {
    @NotBlank
    @UUID
    private String bookingId;

    @JsonIgnore
    @UUID
    private String userContextId;
}
